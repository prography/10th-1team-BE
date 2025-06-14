package org.prography.bff.search.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.response.CursorResponse
import org.prography.bff.search.controller.model.AutoCompleteResponseDTO
import org.prography.bff.search.controller.model.PlaceDetailDTO
import org.prography.bff.search.controller.model.SearchResponseDTO
import org.prography.bff.search.controller.model.enumeration.FoodCategory
import org.prography.bff.search.controller.model.enumeration.OrderStrategy

interface SearchController {
    @Operation(
        summary = "자동 완성 API",
        description = "상호명 앞자리가 동일한 부분을 검색",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = AutoCompleteResponseDTO::class),
                                arraySchema = Schema(description = "Cursor-paginated list"),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun autoComplete(
        keyword: String,
        size: Int,
        addressCodes: List<String>?,
    ): ApiResponse<List<AutoCompleteResponseDTO>>

    @Operation(
        summary = "검색 중 & 검색 완료 API",
        description = "기본적인 Elasticsearch 매치 쿼리 기본정렬로 반환 됩니다. 마지막 아이디는 리스트뷰의 마지막 원소의 ID 값을 넣으면 조회 됩니다. (hasNext === false 일 경우 다음 스크롤에 불러들일 리스트가 없습니다.)",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = SearchResponseDTO::class),
                                arraySchema = Schema(description = "Cursor-paginated list"),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun searchTerm(
        @Parameter(
            name = "keyword",
            `in` = ParameterIn.QUERY,
            description = "검색어 (음식점 이름 또는 설명 등에서 일치 검색에 사용)",
            required = false,
            example = "치킨",
        )
        keyword: String,
        @Parameter(
            name = "size",
            `in` = ParameterIn.QUERY,
            description = "한 번에 조회할 결과 개수",
            required = true,
            example = "20",
        )
        size: Int,
        @Parameter(
            name = "lastId",
            `in` = ParameterIn.QUERY,
            description = "이전 페이지의 마지막 ID. 이 ID 이후부터 다음 페이지 조회",
            required = false,
            example = "더라운지@서울_서초구_강남대로107길_6",
        )
        lastId: String?,
        @Parameter(
            name = "dong_code",
            `in` = ParameterIn.QUERY,
            description = "검색을 제한할 구 코드 리스트 (여러 개 전달 가능)",
            required = false,
            example = "[\"11110\",\"11140\"]",
        )
        addressCodes: List<String>?,
        categories: List<FoodCategory>?,
        @Parameter(
            name = "sort",
            `in` = ParameterIn.QUERY,
            description = "정렬 기준 (예: RELATED, AVERAGE_RATING_HIGH)",
            required = false,
            example = "RELATED",
        )
        sort: OrderStrategy?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>>

    @Operation(
        summary = "음식점 세부 페이지 정보",
        description = "부족한 정보는 말해주세요.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PlaceDetailDTO::class),
                    ),
                ],
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "데이터가 수집되지 않은 음식점인 경우",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiResponse.Failure::class),
                    ),
                ],
            ),
        ],
    )
    fun getMockSummary(placeId: String): ApiResponse<PlaceDetailDTO>
}
