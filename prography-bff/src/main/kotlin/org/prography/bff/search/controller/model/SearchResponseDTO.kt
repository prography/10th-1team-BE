package org.prography.bff.search.controller.model

/**
 * 검색 결과
 */
data class SearchResponseDTO(
    /**
     * 장소 아이디
     */
    val id: String,
    /**
     * 장소 주소
     */
    val addresses: String,
    /**
     * 장소 도로명 주소
     */
    val roadAddresses: String,
    /**
     * 장소 지역 관련 정보
     */
    val region: Region,
    /**
     * 업체 카테고리
     */
    val category: String,
    /**
     * 상호명
     */
    val name: String,
    /**
     * 대표 이미지 주소
     */
    val imageUrl: String?,
    /**
     * 카카오 리뷰 정보
     */
    val kakao: ReviewSummary,
    /**
     * 네이버 리뷰 정보
     */
    val naver: ReviewSummary,
)
