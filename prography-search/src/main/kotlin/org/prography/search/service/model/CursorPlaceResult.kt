package org.prography.search.service.model

/**
 * Cursor based pagination 반환 객체
 */
data class CursorPlaceResult(
    /**
     * 검색 결과 리스트
     */
    val result: List<PlaceSearchResult> = emptyList(),
    /**
     * 다음 조회 가능 여부
     */
    val hasNext: Boolean,
)
