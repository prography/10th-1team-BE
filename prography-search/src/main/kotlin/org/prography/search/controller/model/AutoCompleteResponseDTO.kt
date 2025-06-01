package org.prography.search.controller.model

/**
 * 자동완성 응답 값
 */
data class AutoCompleteResponseDTO(
    /**
     * 장소 아이디
     */
    val id: String,
    /**
     * 장소 지역 정보
     */
    val region: Region,
    /**
     * 도로명 주소, 카카오 도로명 주소 기반
     */
    val roadAddresses: String,
    /**
     * 카테고리 중 가장 마지막 값
     */
    val category: String?,
    /**
     * 상호명, 카카오 상호명 기반
     */
    val name: String,
)
