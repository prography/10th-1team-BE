package org.prography.autocomplete.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType
import org.prography.autocomplete.controller.model.AutoCompleteResponseDTO
import org.prography.domain.RestaurantPlace
import org.springframework.stereotype.Service

@Service
class AutoCompleteService(
    private val client: ElasticsearchClient,
) {
    private val index = "restaurant_place"

    /**
     * 2) 자동완성용: 입력값이 접두사로 시작하는 placeName(keyword)만
     */
    fun autocompleteByName(
        prefix: String,
        size: Int,
    ): List<AutoCompleteResponseDTO> {
        val resp =
            client.search({ req ->
                req.index(index)
                    .size(size)
                    .query { q ->
                        q.multiMatch { mm ->
                            mm.query(prefix)
                                .type(TextQueryType.BoolPrefix) // bool_prefix: 앞부분 일치 검색
                                .fields(
                                    listOf(
                                        "id^3", // id 에 매칭되면 점수 3배
                                        "kakaoPlaceName.keyword",
                                        "kakaoRoadAddress.keyword",
                                    ),
                                )
                        }
                    }
            }, RestaurantPlace::class.java)

        return resp.hits().hits().map { hit ->
            val src = hit.source()!!
            AutoCompleteResponseDTO(
                id = src.id,
                dongCode = src.dongCode,
                roadAddresses = src.kakaoRoadAddress,
                categories = src.category.firstOrNull(),
                name = src.kakaoPlaceName,
            )
        }
    }
}
