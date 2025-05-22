package org.prography.restaurant.kakao.review

data class KakaoReview(
    var reviewId: Long,
    val starRating: Int,
    val content: String,
    var photoCount: Int,
    var photos: List<KakaoPhoto>? = mutableListOf(),
    var strengthIds: List<Int>? = null,
    var registeredAt: String,
    var updatedAt: String,
    var ownerData: KakaoOwnerData,
)
