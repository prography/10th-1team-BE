package org.prography.search.service

import org.prography.search.service.model.AutoCompleteResult
import org.prography.search.service.model.CursorPlaceResult
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
}
