package org.prography.search.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.model.PlaceDetailDTO
import org.prography.search.model.PlaceSummaryDTO
import org.prography.search.model.enumeration.SortType

interface SearchController {
    @Operation(
        summary = "검색 결과 API",
        description = "요청의 기준에 따라 적합한 음식점 정보 리스트를 리스트업합니다.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = PlaceSummaryDTO::class),
                                arraySchema = Schema(description = "Cursor-paginated list"),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getMockSummaryList(
        @Parameter(
            name = "keyword",
            `in` = ParameterIn.QUERY,
            description = "검색어 (음식점 이름 또는 설명 등에서 일치 검색에 사용)",
            required = false,
            example = "치킨",
        )
        keyword: String?,
        @Parameter(
            name = "lastId",
            `in` = ParameterIn.QUERY,
            description = "이전 페이지의 마지막 ID. 이 ID 이후부터 다음 페이지 조회",
            required = false,
            example = "더라운지@서울_서초구_강남대로107길_6",
        )
        lastId: String?,
        @Parameter(
            name = "size",
            `in` = ParameterIn.QUERY,
            description = "한 번에 조회할 결과 개수",
            required = true,
            example = "20",
        )
        size: Int,
        @Parameter(
            name = "dongCodes",
            `in` = ParameterIn.QUERY,
            description = "검색을 제한할 구 코드 리스트 (여러 개 전달 가능)",
            required = false,
            example = "[\"11110\",\"11140\"]",
        )
        dongCodes: List<String>?,
        @Parameter(
            name = "sortType",
            `in` = ParameterIn.QUERY,
            description = "정렬 기준 (예: GENERAL, MOST_REVIEW)",
            required = true,
            example = "GENERAL",
        )
        sortType: SortType,
    ): ApiResponse<CursorResponse<PlaceSummaryDTO>>

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
