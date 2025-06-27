package org.prography.bff.restaurant.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.restaurant.controller.model.PlaceDetailDTO
import org.prography.bff.restaurant.controller.model.strength.StrengthScoresDto
import org.prography.bff.restaurant.service.RestaurantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurant")
class RestaurantControllerImpl(
    private val restaurantService: RestaurantService,
) : RestaurantController {
    @GetMapping("/detail/{placeId}")
    override fun getPlaceSummary(
        @PathVariable placeId: String,
    ): ApiResponse<PlaceDetailDTO> {
        val detail = restaurantService.getPlaceDetail(placeId)

        val data =
            PlaceDetailDTO(
                kakaoPlaceUri = detail.kakaoPlaceUri,
                naverPlaceUri = detail.naverPlaceUri,
                name = detail.name,
                addressName = detail.addressName,
                roadAddressName = detail.roadAddressName,
                x = detail.x,
                y = detail.y,
                summaryAI = detail.summaryAI,
                photos = detail.photos,
                strengthScoresDto = StrengthScoresDto.fromDomain(detail.strengthScores),
                kakaoReviewCount = detail.kakaoReviewCount,
                kakaoReviewAvgScore = detail.kakaoReviewAvgScore,
                kakaoReviews = detail.kakaoReviews,
                naverReviewCount = detail.naverReviewCount,
                naverReviewAvgScore = detail.naverReviewAvgScore,
                naverReviews = detail.naverReviews,
                dongName = detail.dongName,
            )

        return ApiResponse.success(data)
    }
}
