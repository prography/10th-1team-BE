package org.prography.bff.search.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.response.CursorResponse
import org.prography.bff.region.domain.service.RegionService
import org.prography.bff.search.controller.mapper.CategoryMapper
import org.prography.bff.search.controller.mapper.StrategyMapper
import org.prography.bff.search.controller.model.AutoCompleteResponseDTO
import org.prography.bff.search.controller.model.Region
import org.prography.bff.search.controller.model.ReviewSummary
import org.prography.bff.search.controller.model.SearchResponseDTO
import org.prography.bff.search.controller.model.enumeration.FoodCategory
import org.prography.bff.search.controller.model.enumeration.OrderStrategy
import org.prography.search.service.PlaceSearchService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchControllerImpl(
    private val placeSearchService: PlaceSearchService,
    private val regionService: RegionService,
) : SearchController {
    @GetMapping("/auto")
    override fun autoComplete(
        @RequestParam(required = true, name = "keyword") keyword: String,
        @RequestParam(required = false, name = "size") size: Int?,
        @RequestParam(required = false, name = "dong_code") addressCodes: List<String>?,
        @RequestParam(required = false, name = "category") foodCategory: FoodCategory?,
    ): ApiResponse<List<AutoCompleteResponseDTO>> {
        val data =
            placeSearchService.autoCompleteByKeyword(
                keyword = keyword,
                size = size ?: 5,
                addressCodes = addressCodes ?: emptyList(),
                category = CategoryMapper.service(foodCategory ?: FoodCategory.UNDEFINED),
            ).map { (id, legalCode, _, roadAddresses, category, name) ->
                AutoCompleteResponseDTO(
                    id = id,
                    region = Region(regionService.findRegionByBCode(legalCode), legalCode),
                    roadAddresses = roadAddresses,
                    category = category,
                    name = name,
                )
            }.toList()
        return ApiResponse.success(data)
    }

    @GetMapping
    override fun searchTerm(
        @RequestParam(required = true, name = "keyword") keyword: String,
        @RequestParam(required = false, name = "size") size: Int?,
        @RequestParam(required = false, name = "dong_code") addressCodes: List<String>?,
        @RequestParam(required = false, name = "category") foodCategory: FoodCategory?,
        @RequestParam(required = false, name = "sort") sort: OrderStrategy?,
        @RequestParam(required = false, name = "cursor") cursorString: String?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>> {
        val cursorSearch =
            placeSearchService.cursorSearchByKeyword(
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
                    addresses = result.address,
                    roadAddresses = result.roadAddress,
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
                    region =
                        Region(
                            regionService.findRegionByBCode(result.legalCode),
                            result.legalCode,
                        ),
                )
            }.toList()

        return ApiResponse.success(
            CursorResponse(
                total = cursorSearch.total,
                content = data,
                cursor = cursorSearch.cursor,
                hasNext = cursorSearch.hasNext,
            ),
        )
    }

    @GetMapping("/recommend")
    override fun recommandPlace(
        @RequestParam(required = false, name = "size") size: Int?,
        @RequestParam(required = false, name = "dong_code") addressCodes: List<String>,
    ): ApiResponse<List<SearchResponseDTO>> {
        val data: List<SearchResponseDTO> =
            placeSearchService.recommendPlace(
                size =
                    size
                        ?: 3,
                addressCodes = addressCodes,
            ).map {
                SearchResponseDTO(
                    id = it.id,
                    addresses = it.address,
                    roadAddresses = it.roadAddress,
                    category = it.category,
                    name = it.name,
                    imageUrl = it.imageUrl,
                    kakao =
                        ReviewSummary(
                            count = it.kakaoReviewCount,
                            score = it.kakaoScore,
                            processed = it.kakaoReview,
                        ),
                    naver =
                        ReviewSummary(
                            count = it.naverReviewCount,
                            score = it.naverScore,
                            processed = it.naverReview,
                        ),
                    region = Region(regionService.findRegionByBCode(it.legalCode), it.legalCode),
                )
            }

        return ApiResponse.success(data = data)
    }
}
