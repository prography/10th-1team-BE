package org.prography.search.service.model

data class PlaceSearchResult(
    val id: String,
    val dongCode: String,
    val addresses: String,
    val roadAddresses: String,
    val category: String?,
    val name: String,
    val imageUrl: String?,
    val kakaoReviewCount: Long = 0,
    val kakaoScore: Float = 0.0f,
    val kakaoReview: Boolean = false,
    val naverReviewCount: Long = 0,
    val naverScore: Float = 0.0f,
    val naverReview: Boolean = false,
)
