package org.prography.bff.bookmark.service.model

import java.util.UUID

/**
 * 그룹 정보와 저장된 가게 반환
 */
data class PlaceGroupWithPlaces(
    /**
     * 그룹 아이디
     */
    val placeGroupId: UUID,
    /**
     * 그룹 이름
     */
    val placeGroupName: String,
    /**
     * 그룹 지정된 아이콘
     */
    val placeGroupIcon: String,
    /**
     * 저장된 가게의 수
     */
    val numberOfPlace: Long,
    /**
     * 저장된 가게 목록
     */
    val places: List<Place>,
)
