package org.prography.naver.review.domain

data class NaverReview(
    var id: String,
    var reviewId: String,
    var originType: String,
    var rating: Double?,
    var body: String,
    var visitCount: Int?,
    var medias: List<NaverMedia>? = mutableListOf(),
    var naverOwnerData: NaverOwnerData,
    var registeredAt: String,
)
