package org.prography.search.controller

import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.model.PlaceDetailDTO
import org.prography.search.model.PlaceSummaryDTO
import org.prography.search.service.mock.MockService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val mockService: MockService,
) {
    @GetMapping("/search/mock")
    fun getMockSummaryList(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) lastId: Long?,
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): ResponseEntity<ApiResponse<CursorResponse<PlaceSummaryDTO>>> {
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

        return ResponseEntity.ok(
            ApiResponse.success(
                CursorResponse(
                    summaries,
                    hasNext,
                ),
            ),
        )
    }

    @GetMapping("/detail/mock/{id}")
    fun getMockSummaryList(
        @PathVariable(value = "id") placeId: Long,
    ): ResponseEntity<ApiResponse<PlaceDetailDTO>> {
        val detail = mockService.getPlaceDetail(placeId)

        val data =
            PlaceDetailDTO(
                detail.kakaoPlaceUri,
                detail.naverPlaceUri,
                detail.name,
                detail.addressName,
                detail.roadAddressName,
                detail.photos,
                detail.keywords,
                detail.reviewSummary,
                detail.kakaoReviewCount,
                detail.kakaoReviewAvgScore,
                detail.kakaoReviews,
                detail.kakaoVoteRate,
                detail.naverReviewCount,
                detail.naverReviewAvgScore,
                detail.naverReviews,
                detail.naverVoteRate,
            )

        return ResponseEntity.ok(
            ApiResponse.success(
                data,
            ),
        )
    }
}
