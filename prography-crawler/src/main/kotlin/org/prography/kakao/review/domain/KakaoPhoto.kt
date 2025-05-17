package org.prography.kakao.review.domain

data class KakaoPhoto(
    var url: String,
    var photoId: Long,
    var reviewId: Long,
    var updatedAt: String,
)
