package org.prography.bff.restaurant.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.restaurant.controller.model.PlaceDetailDTO
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerResponse

@Tag(
    name = "Restaurant",
    description = "음식점 상세 정보 및 리뷰 요약 조회 API",
)
interface RestaurantController {
    @Operation(
        summary = "음식점 세부 페이지 정보",
        description = "음식점과 음식점 리뷰에 관한 정보를 반환합니다.",
        responses = [
            SwaggerResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PlaceDetailDTO::class),
                    ),
                ],
            ),
            SwaggerResponse(
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
    fun getPlaceSummary(placeId: String): ApiResponse<PlaceDetailDTO>
}
