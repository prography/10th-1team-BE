package org.prography.search.service

import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.CursorPlaceResult
import org.prography.search.service.model.PlaceSearchResult
import org.prography.search.service.model.enumeration.FilterCategory
import org.prography.search.service.model.enumeration.SortingStrategy

/**
 * 장소 검색을 위한 인터페이스
 */
interface PlaceSearchService {
    fun autoCompleteByKeyword(
        keyword: String,
        size: Int,
        addressCodes: List<String>,
        category: FilterCategory?,
    ): List<AutoCompleteResult>

    fun cursorSearchByKeyword(
        keyword: String,
        size: Int,
        cursorString: String?,
        addressCodes: List<String>,
        category: FilterCategory?,
        strategy: SortingStrategy = SortingStrategy.RELATED,
    ): CursorPlaceResult

    fun recommendPlace(
        size: Int,
        addressCodes: List<String>,
    ): List<PlaceSearchResult>
}
