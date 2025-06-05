package org.prography.search.controller

import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.model.PlaceDetailDTO
import org.prography.search.model.PlaceSummaryDTO
import org.prography.search.model.enumeration.SortType
import org.prography.search.model.strength.StrengthScoresDto
import org.prography.search.service.mock.MockService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchControllerImpl(
    private val mockService: MockService,
) : SearchController {
    @GetMapping("/mock")
    override fun getMockSummaryList(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) lastId: String?,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false) guCodes: List<String>?,
        @RequestParam(required = false, defaultValue = "GENERAL") sortType: SortType,
    ): ApiResponse<CursorResponse<PlaceSummaryDTO>> {
        val codes = guCodes ?: emptyList()
        val summaries =
            mockService.getSummaries(keyword, lastId, size)
                .stream()
                .map {
                    PlaceSummaryDTO(
                        it.id,
                        it.name,
                        it.addressName,
                        it.roadAddressName,
                        it.kakaoReviews,
                        it.kakaoScore,
                        it.naverReviews,
                        it.naverScore,
                    )
                }.toList()
        val hasNext = mockService.isLatest(summaries.last().id)

        return ApiResponse.success(
            CursorResponse(
                summaries,
                hasNext,
            ),
        )
    }

    @GetMapping("/detail/{id}")
    override fun getMockSummary(
        @PathVariable(value = "id") placeId: String,
    ): ApiResponse<PlaceDetailDTO> {
        val detail = mockService.getPlaceDetail(placeId)

        val data =
            PlaceDetailDTO(
                detail.kakaoPlaceUri,
                detail.naverPlaceUri,
                detail.name,
                detail.addressName,
                detail.roadAddressName,
                detail.dongCode,
                detail.photos,
                StrengthScoresDto.fromDomain(detail.strengthScores),
                detail.kakaoReviewCount,
                detail.kakaoReviewAvgScore,
                detail.kakaoReviews,
                detail.kakaoVoteRate,
                detail.naverReviewCount,
                detail.naverReviewAvgScore,
                detail.naverReviews,
                detail.naverVoteRate,
            )

        return ApiResponse.success(data)
    }
}
