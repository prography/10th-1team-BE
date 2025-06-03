package org.prography.bff.restaurant.kakao.review

data class KakaoReviewData(
    var score: KakaoScoreSet,
    var reviews: List<KakaoReview>,
)
