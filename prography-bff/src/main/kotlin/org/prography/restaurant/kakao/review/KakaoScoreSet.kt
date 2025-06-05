package org.prography.restaurant.kakao.review

data class KakaoScoreSet(
    var reviewCount: Int,
    var totalScore: Int,
    var averageScore: Double,
    var strengthCounts: List<StrengthCount>,
    var strengthUv: Int,
)
