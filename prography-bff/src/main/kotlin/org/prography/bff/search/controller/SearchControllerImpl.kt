package org.prography.bff.search.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.response.CursorResponse
import org.prography.bff.search.controller.mapper.CategoryMapper
import org.prography.bff.search.controller.mapper.StrategyMapper
import org.prography.bff.search.controller.model.AutoCompleteResponseDTO
import org.prography.bff.search.controller.model.PlaceDetailDTO
import org.prography.bff.search.controller.model.Region
import org.prography.bff.search.controller.model.ReviewSummary
import org.prography.bff.search.controller.model.SearchResponseDTO
import org.prography.bff.search.controller.model.enumeration.FoodCategory
import org.prography.bff.search.controller.model.enumeration.OrderStrategy
import org.prography.bff.search.model.strength.StrengthScoresDto
import org.prography.bff.search.service.mock.MockService
import org.prography.search.service.SearchService
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
        @RequestParam(required = true, name = "keyword") keyword: String,
        @RequestParam(required = false, name = "size") size: Int?,
        @RequestParam(required = false, name = "dong_code") addressCodes: List<String>?,
        @RequestParam(required = false, name = "category") foodCategory: FoodCategory?,
    ): ApiResponse<List<AutoCompleteResponseDTO>> {
        val data =
            searchService.autoCompleteByKeyword(
                keyword = keyword,
                size = size ?: 5,
                addressCodes = addressCodes ?: emptyList(),
                category = CategoryMapper.service(foodCategory ?: FoodCategory.UNDEFINED),
            ).map { (id, legalCode, _, roadAddresses, category, name) ->
                AutoCompleteResponseDTO(
                    id = id,
                    region = Region("법정동 이름", legalCode),
                    roadAddresses = roadAddresses,
                    category = category,
                    name = name,
                )
            }.toList()
        return ApiResponse.success(data)
    }

    @GetMapping("")
    override fun searchTerm(
        @RequestParam(required = true, name = "keyword") keyword: String,
        @RequestParam(required = false, name = "size") size: Int?,
        @RequestParam(required = false, name = "dong_code") addressCodes: List<String>?,
        @RequestParam(required = false, name = "category") foodCategory: FoodCategory?,
        @RequestParam(required = false, name = "sort") sort: OrderStrategy?,
        @RequestParam(required = false, name = "cursor") cursorString: String?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>> {
        val cursorSearch =
            searchService.cursorSearchByKeyword(
                keyword = keyword,
                size = size ?: 5,
                cursorString = cursorString,
                addressCodes = addressCodes ?: emptyList(),
                category = CategoryMapper.service(foodCategory ?: FoodCategory.UNDEFINED),
                strategy = StrategyMapper.service(sort ?: OrderStrategy.RELATED),
            )

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
                cursor = cursorSearch.cursor,
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
                detail.bCode,
                detail.x,
                detail.y,
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
