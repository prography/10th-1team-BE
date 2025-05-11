package org.prography.search.model

data class PlaceSummaryDTO(
    val id: Long,
    val name: String,
    val addressName: String,
    val roadAddressName: String,
    val kakaoReviews: Int,
    val kakaoScore: Double,
    val naverReviews: Int,
    val naverScore: Double
)
