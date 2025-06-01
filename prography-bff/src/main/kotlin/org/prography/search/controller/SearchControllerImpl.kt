package org.prography.search.controller

import org.prography.config.response.ApiResponse
import org.prography.config.response.CursorResponse
import org.prography.search.controller.model.AutoCompleteResponseDTO
import org.prography.search.controller.model.Region
import org.prography.search.controller.model.ReviewSummary
import org.prography.search.controller.model.SearchResponseDTO
import org.prography.search.controller.model.enumeration.FoodCategory
import org.prography.search.controller.model.enumeration.OrderStrategy
import org.prography.search.model.PlaceDetailDTO
import org.prography.search.model.strength.StrengthScoresDto
import org.prography.search.service.SearchService
import org.prography.search.service.mock.MockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchControllerImpl(
    private val mockService: MockService,
    private val searchService: SearchService,
) : SearchController {
    @GetMapping("/auto")
    override fun autoComplete(
        @RequestParam(name = "keyword") keyword: String,
        @RequestParam(name = "size", defaultValue = "5") size: Int,
        @RequestParam(name = "dong_code", required = false) addressCodes: List<String>?,
    ): ApiResponse<List<AutoCompleteResponseDTO>> {
        val data =
            searchService.autoCompleteByKeyword(keyword, size).map { result ->
                AutoCompleteResponseDTO(
                    id = result.id,
                    region = Region("법정동 이름", result.legalCode),
                    roadAddresses = result.roadAddresses,
                    category = result.category,
                    name = result.name,
                )
            }.toList()
        return ApiResponse.success(data)
    }

    @GetMapping("")
    override fun searchTerm(
        @RequestParam(name = "keyword") keyword: String,
        @RequestParam(name = "size", defaultValue = "5") size: Int,
        @RequestParam(name = "last_id", required = false) lastId: String?,
        @RequestParam(name = "dong_code", required = false) addressCodes: List<String>?,
        @RequestParam(name = "categories", required = false) categories: List<FoodCategory>?,
        @RequestParam(name = "sort", required = false) sort: OrderStrategy?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>> {
        val cursorSearch = searchService.cursorSearchByKeyword(keyword, size, lastId, addressCodes, emptyList())
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
                    region = Region("법정동 이름", result.legalCode),
                )
            }.toList()

        return ApiResponse.success(
            CursorResponse(
                content = data,
                hasNext = cursorSearch.hasNext,
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
