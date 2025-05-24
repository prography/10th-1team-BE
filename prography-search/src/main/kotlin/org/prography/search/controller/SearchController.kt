package org.prography.search.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.controller.model.AutoCompleteResponseDTO
import org.prography.search.controller.model.SearchResponseDTO

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
    ): ApiResponse<List<AutoCompleteResponseDTO>>

    @Operation(
        summary = "자동 완성 API ( Cursor Pagination )",
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
    fun cursorAutoComplete(
        keyword: String,
        size: Int,
        lastId: String?,
    ): ApiResponse<CursorResponse<AutoCompleteResponseDTO>>

    @Operation(
        summary = "검색 중 & 검색 완료 API",
        description = "기본적인 Elasticsearch 매치 쿼리 기본정렬로 반환 됩니다. 마지막 아이디는 리스트뷰의 마지막 원소의 ID값을 넣으면 조회 됩니다. (hasNext === false 일 경우 다음 스크롤에 불러들일 리스트가 없습니다.)",
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
        keyword: String,
        size: Int,
        lastId: String?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>>
}
