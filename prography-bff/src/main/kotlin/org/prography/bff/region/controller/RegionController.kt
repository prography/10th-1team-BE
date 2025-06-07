package org.prography.bff.region.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.region.model.RegionDto

interface RegionController {
    @Operation(
        summary = "검색 가능한 지역 정보 API",
        description = "최소 단위 '동' 기준으로 리턴합니다.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = org.prography.bff.region.model.RegionDto::class),
                    ),
                ],
            ),
        ],
    )
    fun getSearchableRegions(): ApiResponse<RegionDto>
}
