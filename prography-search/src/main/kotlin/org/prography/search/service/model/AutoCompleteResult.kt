package org.prography.search.service.model

/**
 * 자동완성 결과 값
 */
data class AutoCompleteResult(
    /**
     * 몽고 디비 조회용 ID
     */
    val id: String,
    /**
     * 법정 동 코드
     */
    val legalCode: String,
    /**
     * 행정 동 코드
     */
    val administrativeCode: String,
    /**
     * 카카오 도로명 주소 기반, 도로명 주소
     */
    val roadAddresses: String,
    /**
     * 카테고리 중 가장 마지막 값
     */
    val category: String,
    /**
     * 카카오 상호명 기반
     */
    val name: String,
)
