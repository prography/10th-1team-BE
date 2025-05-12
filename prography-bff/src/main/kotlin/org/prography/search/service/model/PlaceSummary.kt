package org.prography.search.service.model

data class PlaceSummary(
    val id: Long,
    val name: String,
    val addressName: String,
    val roadAddressName: String,
    val kakaoReviews: Int,
    val kakaoScore: Double,
    val naverReviews: Int,
    val naverScore: Double,
)
