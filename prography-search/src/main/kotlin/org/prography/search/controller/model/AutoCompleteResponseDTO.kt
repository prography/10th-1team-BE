package org.prography.search.controller.model

/**
 * 자동완성 응답 값
 */
data class AutoCompleteResponseDTO(
    /**
     * 몽고 디비 조회용 ID
     */
    val id: String,
    /**
     * 동 코드
     */
    val dongCode: String?,
    /**
     * 카카오 도로명 주소 기반, 도로명 주소
     */
    val roadAddresses: String,
    /**
     * 카테고리 중 가장 마지막 값
     */
    val category: String?,
    /**
     * 카카오 상호명 기반
     */
    val name: String,
)
