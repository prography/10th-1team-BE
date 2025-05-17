package org.prography.naver.review.domain

data class NaverReviewData(
    var reviews: List<NaverReview>,
    var score: NaverScoreSet?,
)
