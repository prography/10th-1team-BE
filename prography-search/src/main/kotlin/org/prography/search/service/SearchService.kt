package org.prography.search.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.SortOptions
import co.elastic.clients.elasticsearch._types.SortOrder
import org.prography.domain.RestaurantPlace
import org.prography.search.controller.model.enumeration.FoodCategory
import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.CursorPlaceResult
import org.prography.search.service.model.PlaceSearchResult
import org.springframework.stereotype.Service

/**
 * Elasticsearch 를 이용한 검색 관련 서비스
 */
@Service
class SearchService(
    private val client: ElasticsearchClient,
) {
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
    }

    /**
     *
     */
    fun cursorSearchByKeyword(
        keyword: String,
        size: Int,
        lastId: String? = null,
        addressCodes: List<String>? = emptyList(),
        categories: List<FoodCategory>? = emptyList(),
    ): CursorPlaceResult {
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
    }
}
