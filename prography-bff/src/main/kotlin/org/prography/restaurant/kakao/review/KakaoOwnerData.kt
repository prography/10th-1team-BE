package org.prography.restaurant.kakao.review

data class KakaoOwnerData(
    var id: String,
    var nickname: String,
    var profileImageUrl: String,
    var reviewCount: Int,
    var averageScore: Double,
)
