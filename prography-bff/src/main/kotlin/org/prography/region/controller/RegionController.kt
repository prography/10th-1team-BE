package org.prography.region.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.config.response.ApiResponse
import org.prography.region.model.RegionDto
import org.springframework.http.ResponseEntity

interface RegionController {
    @Operation(
        summary = "검색 가능한 지역 정보 API",
        description = "시, 군, 구 기준으로 리턴합니다.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = RegionDto::class),
                    ),
                ],
            ),
        ],
    )
    fun getSearchableRegions(): ResponseEntity<ApiResponse<RegionDto>>
}
