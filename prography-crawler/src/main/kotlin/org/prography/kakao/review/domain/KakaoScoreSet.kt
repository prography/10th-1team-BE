package org.prography.kakao.review.domain

import org.prography.kakao.review.external.dto.StrengthCount

data class KakaoScoreSet(
    var reviewCount: Int,
    var totalScore: Int,
    var averageScore: Double,
    var strengthCounts: List<StrengthCount>,
    var strengthUv: Int,
)
