package org.prography.bff.region.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.region.model.RegionDto

@Tag(
    name = "Region",
    description = "지역 정보 전용 API",
)
interface RegionController {
    @Operation(
        summary = "검색 가능한 지역 정보 API",
        description = "최소 단위 '시군구' 기준으로 리턴합니다.",
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
    fun getSearchableRegions(): ApiResponse<RegionDto>
}
