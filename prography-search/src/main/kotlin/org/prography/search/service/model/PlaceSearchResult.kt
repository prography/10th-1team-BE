package org.prography.search.service.model

import org.prography.search.domain.GeoPoint

/**
 * 검색 결과
 */
data class PlaceSearchResult(
    /**
     * 아이디
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
     * 주소
     */
    val address: String,
    /**
     * 도로명 주소
     */
    val roadAddress: String,
    /**
     * 음식점 카테고리
     */
    val category: String,
    /**
     * 상호명
     */
    val name: String,
    /**
     * 대표 이미지 주소
     */
    val imageUrl: String,
    /**
     * 카카오 리뷰 갯수
     */
    val kakaoReviewCount: Long,
    /**
     * 카카오 리뷰 점수
     */
    val kakaoScore: Double,
    /**
     * 카카오 리뷰 존재 유무
     */
    val kakaoReview: Boolean,
    /**
     * 네이버 리뷰 갯수
     */
    val naverReviewCount: Long,
    /**
     * 네이버 리뷰 점수
     */
    val naverScore: Double,
    /**
     * 네이버 리뷰 존재 유무
     */
    val naverReview: Boolean,
    /**
     * 위치 정보
     */
    val location: GeoPoint,
)
