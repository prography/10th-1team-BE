package org.prography.search.service

import org.prography.search.domain.GeoPoint
import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.CursorPlaceResult
import org.prography.search.service.model.PlaceSearchResult
import org.prography.search.service.model.enumeration.FilterCategory
import org.prography.search.service.model.enumeration.SortingStrategy

class FakeSearchService : PlaceSearchService {
    override fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
        addressCodes: List<String>,
        category: FilterCategory?,
    ): List<AutoCompleteResult> {
        return emptyList()
    }

    override fun cursorSearchByKeyword(
        keyword: String,
        size: Int,
        cursorString: String?,
        addressCodes: List<String>,
        category: FilterCategory?,
        strategy: SortingStrategy,
    ): CursorPlaceResult {
        return CursorPlaceResult(hasNext = false)
    }

    override fun recommendPlace(
        size: Int,
        addressCodes: List<String>,
    ): List<PlaceSearchResult> {
        return listOf(
            PlaceSearchResult(
                id = "음식점 아이디",
                legalCode = "1160000000",
                administrativeCode = "행정동 코드",
                address = "주소",
                roadAddress = "도로명 주소",
                category = "카테고리",
                name = "상호명",
                imageUrl = "",
                kakaoReviewCount = 0L,
                kakaoScore = 0.0,
                kakaoReview = true,
                naverReviewCount = 0L,
                naverScore = 0.0,
                naverReview = true,
                location = GeoPoint(0.0, 0.0),
            ),
        )
    }
}
