package org.prography.search.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.FieldValue
import co.elastic.clients.elasticsearch._types.SortOptions
import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Operator
import co.elastic.clients.elasticsearch._types.query_dsl.PrefixQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType
import co.elastic.clients.elasticsearch.core.SearchRequest
import org.prography.search.domain.GeoPoint
import org.prography.search.domain.RestaurantPlace
import org.prography.search.exception.ElasticsearchException
import org.prography.search.service.model.AutoCompleteResult
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
        private const val INDEX = "test"
        private const val EMPTY_CATEGORY = "UNDEFINED"
        private const val DEFAULT_LEGAL_CODE = "11680" // 강남구
    }

    fun service(
        keyword: String,
        size: Int,
        cursor: String? = null,
        addressCodes: List<String>,
        category: FilterCategory? = null,
        strategy: SortingStrategy = SortingStrategy.RELATED,
    ): List<PlaceSearchResult> {
        val multiMatchQuery =
            Query.Builder()
                .multiMatch(
                    MultiMatchQuery.Builder()
                        .query(keyword)
                        .fields("place_name")
                        .type(TextQueryType.CrossFields)
                        .operator(Operator.And)
                        .build(),
                )
                .build()

        val boolQueryBuilder = BoolQuery.Builder().must(multiMatchQuery)

        if (addressCodes.isNotEmpty()) {
            val legalFilterQuery =
                Query.Builder()
                    .terms(
                        TermsQuery.Builder()
                            .field("legal")
                            .terms { term -> term.value(addressCodes.map { FieldValue.of(it) }) }
                            .build(),
                    )
                    .build()
            boolQueryBuilder.filter(legalFilterQuery)
        } else {
            val legalPrefixQuery =
                Query.Builder()
                    .prefix(
                        PrefixQuery.Builder()
                            .field("legal")
                            .value(DEFAULT_LEGAL_CODE)
                            .build(),
                    )
                    .build()
            boolQueryBuilder.filter(legalPrefixQuery)
        }

        category?.let {
            val categoryFilterQuery =
                Query.Builder()
                    .term(
                        TermQuery.Builder()
                            .field("category")
                            .value(category.value)
                            .build(),
                    )
                    .build()
            boolQueryBuilder.filter(categoryFilterQuery)
        }

        val reqBuilder =
            SearchRequest.Builder()
                .index(INDEX)
                .query(
                    Query.Builder()
                        .bool(
                            boolQueryBuilder
                                .build(),
                        )
                        .build(),
                )
                .size(size)

        when (strategy) {
            SortingStrategy.RELATED -> {
                reqBuilder
                    .sort { s -> s.score { sc -> sc.order(SortOrder.Desc) } }
            }

            SortingStrategy.AVERAGE_RATING_HIGH -> {
                reqBuilder
                    .sort { s -> s.field { f -> f.field("review_count").order(SortOrder.Asc) } }
                    .sort { s -> s.field { f -> f.field("_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.AVERAGE_RATING_LOW -> {
                reqBuilder
                    .sort { s -> s.field { f -> f.field("review_count").order(SortOrder.Asc) } }
                    .sort { s -> s.field { f -> f.field("_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.REVIEW_COUNT_HIGH -> {
                reqBuilder
                    .sort { s -> s.field { f -> f.field("review_count").order(SortOrder.Asc) } }
                    .sort { s -> s.field { f -> f.field("_id").order(SortOrder.Asc) } }
            }

            SortingStrategy.REVIEW_COUNT_LOW -> {
                reqBuilder
                    .sort { s -> s.field { f -> f.field("review_count").order(SortOrder.Asc) } }
            }
        }

        val searchResponse = client.search(reqBuilder.build(), RestaurantPlace::class.java)

        val hits = searchResponse.hits().hits()
        if (hits.isEmpty()) {
            return emptyList()
        }

        return hits.map { hit ->
            val source = hit.source()!!
            PlaceSearchResult(
                id = source.id,
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
    }

    /**
     *
     */
    fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
        addressCodes: List<String>,
        categories: List<FilterCategory>,
    ): List<AutoCompleteResult> {
        try {
            val multiMatch =
                MultiMatchQuery.Builder()
                    .query(keyword)
                    .fields(
                        "place_name.auto_complete",
                        "place_name.auto_complete._2gram",
                        "place_name.auto_complete._3gram",
                    )
                    .type(TextQueryType.BoolPrefix)
                    .build()

            val multiMatchQuery =
                Query.Builder()
                    .multiMatch(multiMatch)
                    .build()

            val boolQuery =
                BoolQuery.Builder()
                    .must(multiMatchQuery)
                    .build()

            val reqBuilder =
                SearchRequest.Builder()
                    .index(INDEX)
                    .query(Query.Builder().bool(boolQuery).build())
                    .size(size)

            val searchResponse = client.search(reqBuilder.build(), RestaurantPlace::class.java)

            val hits = searchResponse.hits().hits()
            if (hits.isEmpty()) {
                return emptyList()
            }

            return hits.map { hit ->
                val source = hit.source()!!
                AutoCompleteResult(
                    id = source.id,
                    legalCode = source.legal,
                    administrativeCode = source.division,
                    roadAddresses = source.roadAddress,
                    category = EMPTY_CATEGORY,
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

    /**
     *
     */
    fun cursorSearchByKeyword(
        keyword: String,
        size: Int,
        lastId: String? = null,
        addressCodes: List<String>,
        categories: List<FilterCategory>,
        strategy: SortingStrategy? = SortingStrategy.RELATED,
    ): CursorPlaceResult {
        try {
            val resp =
                client.search({ req ->
                    req.index(INDEX)
                        .size(size)
                        .sort(
                            SortOptions.of { sort -> sort.field { source -> source.field("id.keyword").order(SortOrder.Asc) } },
                        )
                        .query { q ->
                            q.wildcard { w ->
                                w.field("id.keyword")
                                    .value("*$keyword*")
                            }
                        }
                        .apply {
                            if (lastId != null) {
                                this.searchAfter(lastId)
                            }
                        }
                }, RestaurantPlace::class.java)
            val hits = resp.hits().hits()
            val next = hits.lastOrNull()?.sort()?.takeIf { it.isNotEmpty() } != null
            val result =
                hits.map { hit ->
                    val source = hit.source()!!
                    PlaceSearchResult(
                        id = source.id,
                        legalCode = source.legal,
                        administrativeCode = source.division,
                        addresses = source.address,
                        roadAddresses = source.roadAddress,
                        category = EMPTY_CATEGORY,
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
                result = result,
                hasNext = next,
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
}
