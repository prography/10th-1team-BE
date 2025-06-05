package org.prography.search.service.model

/**
 * 검색
 */
data class PlaceSearchResult(
    /**
     *
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
    val addresses: String,
    val roadAddresses: String,
    val category: String,
    val name: String,
    val imageUrl: String,
    val kakaoReviewCount: Long,
    val kakaoScore: Float,
    val kakaoReview: Boolean,
    val naverReviewCount: Long,
    val naverScore: Float,
    val naverReview: Boolean,
)
