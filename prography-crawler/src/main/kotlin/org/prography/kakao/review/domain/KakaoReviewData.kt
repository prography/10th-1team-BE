package org.prography.kakao.review.domain

data class KakaoReviewData(
    var score: KakaoScoreSet,
    var reviews: List<KakaoReview>,
)
