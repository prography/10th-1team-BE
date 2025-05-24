package org.prography.search.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import org.prography.domain.RestaurantPlace
import org.prography.search.service.model.AutoComplete
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val client: ElasticsearchClient,
) {
    companion object {
        private const val INDEX = "restaurant_place"
        private const val KEYWORD_KAKAO_PLACE_NAME = "kakaoPlaceName.keyword"
    }

    fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
    ): List<AutoComplete> {
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
            AutoComplete(
                id = source.id,
                dongCode = source.dongCode,
                roadAddresses = source.kakaoRoadAddress,
                category = source.category.lastOrNull(),
                name = source.kakaoPlaceName,
            )
        }
    }
}
