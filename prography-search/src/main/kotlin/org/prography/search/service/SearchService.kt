package org.prography.search.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.FieldValue
import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Operator
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType
import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.search.Hit
import org.prography.search.domain.GeoPoint
import org.prography.search.domain.RestaurantPlace
import org.prography.search.exception.ElasticsearchException
import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.Cursor
import org.prography.search.service.model.CursorPlaceResult
import org.prography.search.service.model.PlaceSearchResult
import org.prography.search.service.model.enumeration.FilterCategory
import org.prography.search.service.model.enumeration.SortingStrategy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.net.ssl.SSLHandshakeException

/**
 * Elasticsearch 를 이용한 검색 관련 서비스
 */
@Service
class SearchService(
    private val client: ElasticsearchClient,
) {
    private val log = LoggerFactory.getLogger(SearchService::class.java)

    companion object {
        private const val INDEX = "restaurant_search"
        private const val EMPTY_CATEGORY = "UNDEFINED"
        private const val DEFAULT_LEGAL_CODE = "11680" // 강남구
    }

    fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
        addressCodes: List<String>,
        category: FilterCategory?,
    ): List<AutoCompleteResult> {
        try {
            val multiMatchQuery =
                Query.Builder()
                    .multiMatch(
                        MultiMatchQuery.Builder()
                            .query(keyword)
                            .fields(
                                "place_name.auto_complete",
                                "place_name.auto_complete._2gram",
                                "place_name.auto_complete._3gram",
                            )
                            .type(TextQueryType.BoolPrefix)
                            .build(),
                    ).build()

            val boolQuery =
                Query.Builder()
                    .bool(
                        createBoolQueryBuilder(addressCodes, category)
                            .must(multiMatchQuery).build(),
                    )
                    .build()

            val request =
                createSearchRequestBuilder()
                    .size(size)
                    .query(boolQuery)
                    .build()

            val searchResponse = client.search(request, RestaurantPlace::class.java)

            val hits = searchResponse.hits().hits()
            if (hits.isEmpty()) {
                return emptyList()
            }

            return hits.map { hit ->
                val source = hit.source()!!
                AutoCompleteResult(
                    id = source.mongoId,
                    legalCode = source.legal,
                    administrativeCode = source.division,
                    roadAddresses = source.address,
                    category = source.category.firstOrNull() ?: EMPTY_CATEGORY,
                    name = source.placeName,
                )
            }
        } catch (sslEx: SSLHandshakeException) {
            // SSL 핸드쉐이크나 인증서 검증에서 실패
            log.error("Elasticsearch SSL certificate validation failed", sslEx)
            throw ElasticsearchException.CertificateValidationException(sslEx)
        } catch (e: Exception) {
            log.error("Unexpected error occurred while searching Elasticsearch", e)
            throw ElasticsearchException.SearchingException(e)
        }
    }

    fun cursorSearchByKeyword(
        keyword: String,
        size: Int,
        cursorString: String?,
        addressCodes: List<String>,
        category: FilterCategory?,
        strategy: SortingStrategy = SortingStrategy.RELATED,
    ): CursorPlaceResult {
        try {
            val fetchSize = size + 1
            val cursor: Cursor? = Cursor.decode(cursorString, strategy)

            val multiMatchQuery =
                Query.Builder()
                    .multiMatch(
                        MultiMatchQuery.Builder()
                            .query(keyword)
                            .fields("place_name", "place_name.raw^5")
                            .type(TextQueryType.CrossFields)
                            .operator(Operator.And)
                            .build(),
                    )
                    .build()

            val boolQuery =
                Query.Builder()
                    .bool(
                        createBoolQueryBuilder(addressCodes, category)
                            .must(multiMatchQuery).build(),
                    )
                    .build()

            val requestBuilder =
                createSearchRequestBuilder(strategy)
                    .size(fetchSize)
                    .query(boolQuery)

            if (cursor != null) {
                requestBuilder.searchAfter(
                    listOf(
                        FieldValue.of(cursor.key),
                        FieldValue.of(cursor.id),
                    ),
                )
            }

            val request = requestBuilder.build()
            val searchResponse = client.search(request, RestaurantPlace::class.java)

            val hit = searchResponse.hits()
            val hits = hit.hits()
            if (hits.isEmpty()) {
                return CursorPlaceResult(result = emptyList(), hasNext = false)
            }

            val pageHits = hits.take(size)
            val lastCursorString = generateCursorString(pageHits, strategy)
            val result =
                pageHits.map {
                    val source = it.source()!!
                    PlaceSearchResult(
                        id = source.mongoId,
                        legalCode = source.legal,
                        administrativeCode = source.division,
                        addresses = source.address,
                        roadAddresses = source.roadAddress,
                        category = source.category.firstOrNull() ?: EMPTY_CATEGORY,
                        name = source.placeName,
                        imageUrl = source.imageUrl,
                        kakaoReviewCount = source.kakaoReviewCount,
                        kakaoScore = source.kakaoScore,
                        kakaoReview = source.kakaoReview,
                        naverReviewCount = source.naverReviewCount,
                        naverScore = source.naverScore,
                        naverReview = source.naverReview,
                        location = source.location ?: GeoPoint(0.0, 0.0),
                    )
                }

            return CursorPlaceResult(
                total = hit.total()?.value().takeIf { cursorString == null } ?: 0L,
                result = result,
                cursor = lastCursorString,
                hasNext = hits.size > size,
            )
        } catch (sslEx: SSLHandshakeException) {
            // SSL 핸드쉐이크나 인증서 검증에서 실패
            log.error("Elasticsearch SSL certificate validation failed", sslEx)
            throw ElasticsearchException.CertificateValidationException(sslEx)
        } catch (e: Exception) {
            log.error("Unexpected error occurred while searching Elasticsearch", e)
            throw ElasticsearchException.SearchingException(e)
        }
    }

    private fun generateCursorString(
        pageHits: List<Hit<RestaurantPlace>>,
        strategy: SortingStrategy,
    ): String {
        val lastSort = pageHits.last().sort()!!

        val nextKey =
            when (strategy) {
                SortingStrategy.RELATED,
                SortingStrategy.AVERAGE_RATING_HIGH,
                SortingStrategy.AVERAGE_RATING_LOW,
                ->
                    lastSort[0].doubleValue()

                SortingStrategy.REVIEW_COUNT_HIGH,
                SortingStrategy.REVIEW_COUNT_LOW,
                ->
                    lastSort[0].longValue()
            }
        return Cursor.encode(nextKey, lastSort[1].stringValue())
    }

    private fun createBoolQueryBuilder(
        addressCodes: List<String>,
        category: FilterCategory?,
    ): BoolQuery.Builder {
        val builder = BoolQuery.Builder()
        val legalQuery: Query =
            if (addressCodes.isNotEmpty()) {
                Query.Builder()
                    .terms(
                        TermsQuery.Builder()
                            .field("legal")
                            .terms { term -> term.value(addressCodes.map { FieldValue.of(it) }) }
                            .build(),
                    )
                    .build()
            } else {
                Query.Builder()
                    .prefix(
                        PrefixQuery.Builder()
                            .field("legal")
                            .value(DEFAULT_LEGAL_CODE)
                            .build(),
                    )
                    .build()
            }

        if (category != null) {
            val categoryQuery =
                Query.Builder()
                    .terms(
                        TermsQuery.Builder()
                            .field("category")
                            .terms { term -> term.value(category.values.map { FieldValue.of(it) }) }
                            .build(),
                    )
                    .build()
            builder.filter(categoryQuery)
        }

        return builder.filter(legalQuery)
    }

    private fun createSearchRequestBuilder(): SearchRequest.Builder {
        return SearchRequest.Builder().index(INDEX)
    }

    private fun createSearchRequestBuilder(strategy: SortingStrategy): SearchRequest.Builder {
        val reqBuilder = SearchRequest.Builder().index(INDEX)

        when (strategy) {
            SortingStrategy.RELATED -> {
                reqBuilder
                    .sort { doc -> doc.score { source -> source.order(SortOrder.Desc) } }
                    .sort { doc -> doc.field { source -> source.field("mongo_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.AVERAGE_RATING_HIGH -> {
                reqBuilder
                    .sort { doc -> doc.field { source -> source.field("review_score").order(SortOrder.Desc) } }
                    .sort { doc -> doc.field { source -> source.field("mongo_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.AVERAGE_RATING_LOW -> {
                reqBuilder
                    .sort { doc -> doc.field { source -> source.field("review_score").order(SortOrder.Asc) } }
                    .sort { doc -> doc.field { source -> source.field("mongo_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.REVIEW_COUNT_HIGH -> {
                reqBuilder
                    .sort { doc -> doc.field { source -> source.field("review_count").order(SortOrder.Desc) } }
                    .sort { doc -> doc.field { source -> source.field("mongo_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.REVIEW_COUNT_LOW -> {
                reqBuilder
                    .sort { doc -> doc.field { source -> source.field("review_count").order(SortOrder.Asc) } }
                    .sort { doc -> doc.field { source -> source.field("mongo_id").order(SortOrder.Asc) } }
            }
        }

        return reqBuilder
    }
}
