package org.prography.search.controller

import io.swagger.v3.oas.annotations.Operation
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
        keyword: String?,
        lastId: String?,
        size: Int,
        guCodes: List<String>?,
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
