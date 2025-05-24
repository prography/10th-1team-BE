package org.prography.search.controller

import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.controller.model.AutoCompleteResponseDTO
import org.prography.search.controller.model.ReviewSummary
import org.prography.search.controller.model.SearchResponseDTO
import org.prography.search.service.SearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchControllerImpl(
    private val searchService: SearchService,
) : SearchController {
    @GetMapping("/auto")
    override fun autoComplete(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "5") size: Int,
    ): ApiResponse<List<AutoCompleteResponseDTO>> {
        val data =
            searchService.autoCompleteByKeyword(keyword, size).map { result ->
                AutoCompleteResponseDTO(
                    id = result.id,
                    dongCode = result.dongCode,
                    roadAddresses = result.roadAddresses,
                    category = result.category,
                    name = result.name,
                )
            }.toList()
        return ApiResponse.success(data)
    }

    @GetMapping("/auto/cursor")
    override fun cursorAutoComplete(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(required = false) lastId: String?,
    ): ApiResponse<CursorResponse<AutoCompleteResponseDTO>> {
        val cursorSearch = searchService.autoCompleteByKeyword(keyword, size, lastId)
        val data =
            cursorSearch.result.map { result ->
                AutoCompleteResponseDTO(
                    id = result.id,
                    dongCode = result.dongCode,
                    roadAddresses = result.roadAddresses,
                    category = result.category,
                    name = result.name,
                )
            }.toList()
        return ApiResponse.success(
            CursorResponse(
                content = data,
                hasNext = cursorSearch.hasNext,
            ),
        )
    }

    @GetMapping("/search")
    override fun searchTerm(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(required = false) lastId: String?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>> {
        val cursorSearch = searchService.cursorSearchByKeyword(keyword, size, lastId)
        val data =
            cursorSearch.result.map { result ->
                SearchResponseDTO(
                    id = result.id,
                    addresses = result.addresses,
                    roadAddresses = result.roadAddresses,
                    category = result.category,
                    name = result.name,
                    imageUrl = result.imageUrl,
                    kakao =
                        ReviewSummary(
                            count = result.kakaoReviewCount,
                            score = result.kakaoScore,
                            processed = result.kakaoReview,
                        ),
                    naver =
                        ReviewSummary(
                            count = result.naverReviewCount,
                            score = result.naverScore,
                            processed = result.naverReview,
                        ),
                )
            }.toList()

        return ApiResponse.success(
            CursorResponse(
                content = data,
                hasNext = cursorSearch.hasNext,
            ),
        )
    }
}
