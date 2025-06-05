package org.prography.search.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.SortOptions
import co.elastic.clients.elasticsearch._types.SortOrder
import org.prography.search.domain.RestaurantPlace
import org.prography.search.exception.ElasticsearchException
import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.CursorPlaceResult
import org.prography.search.service.model.PlaceSearchResult
import org.prography.search.service.model.enumeration.FilterCategory
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
        private const val INDEX = "restaurant_place"
        private const val KEYWORD_KAKAO_PLACE_NAME = "kakaoPlaceName.keyword"
        private const val EMPTY_CATEGORY = "EMPTY"
    }

    /**
     *
     */
    fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
    ): List<AutoCompleteResult> {
        try {
            val resp =
                client.search({ req ->
                    req.index(INDEX)
                        .size(size)
                        .query { q ->
                            q.prefix { p ->
                                p.field(KEYWORD_KAKAO_PLACE_NAME)
                                    .value(keyword)
                            }
                        }
                }, RestaurantPlace::class.java)

            return resp.hits().hits().map { hit ->
                val source = hit.source()!!
                AutoCompleteResult(
                    id = source.id,
                    legalCode = source.legal,
                    administrativeCode = source.division,
                    roadAddresses = source.kakaoRoadAddress,
                    category = source.category.firstOrNull() ?: EMPTY_CATEGORY,
                    name = source.kakaoPlaceName,
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
        addressCodes: List<String>? = emptyList(),
        categories: List<FilterCategory>? = emptyList(),
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
                        addresses = source.kakaoAddress,
                        roadAddresses = source.kakaoRoadAddress,
                        category = source.category.firstOrNull() ?: EMPTY_CATEGORY,
                        name = source.kakaoPlaceName,
                        imageUrl = source.imageUrl,
                        kakaoReviewCount = source.kakaoReviewCount,
                        kakaoScore = source.kakaoScore,
                        kakaoReview = source.kakaoReview,
                        naverReviewCount = source.naverReviewCount,
                        naverScore = source.naverScore,
                        naverReview = source.naverReview,
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
