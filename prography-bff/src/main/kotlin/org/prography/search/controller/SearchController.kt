package org.prography.search.controller

import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.model.PlaceSummaryDTO
import org.prography.search.service.mock.MockService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val mockService: MockService
) {

    @GetMapping("/search/mock")
    fun getMockSummaryList(
        @RequestParam(required = false) keyword: String?,
        @RequestParam(required = false) lastId: Long?,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<ApiResponse<CursorResponse<PlaceSummaryDTO>>> {
        val summaries = mockService.getSummaries(keyword, lastId, size)
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
                    it.naverScore
                )
            }.toList()
        val hasNext = mockService.isLatest(summaries.last().id)

        return ResponseEntity.ok(
            ApiResponse.success(
                CursorResponse(
                    summaries,
                    hasNext
                )
            )
        )
    }
}