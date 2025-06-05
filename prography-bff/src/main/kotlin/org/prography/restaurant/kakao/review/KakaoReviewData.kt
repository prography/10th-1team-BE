package org.prography.restaurant.kakao.review

data class KakaoReviewData(
    var score: KakaoScoreSet,
    var reviews: List<KakaoReview>,
)
