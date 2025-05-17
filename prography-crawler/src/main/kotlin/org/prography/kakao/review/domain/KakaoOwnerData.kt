package org.prography.kakao.review.domain

data class KakaoOwnerData(
    var id: String,
    var nickname: String,
    var profileImageUrl: String,
    var reviewCount: Int,
    var averageScore: Double,
)
