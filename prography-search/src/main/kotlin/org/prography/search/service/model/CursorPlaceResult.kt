package org.prography.search.service.model

data class CursorPlaceResult(
    val result: List<PlaceSearchResult> = emptyList(),
    val hasNext: Boolean,
)
