package org.prography.restaurant.naver.review

data class NaverReviewData(
    var reviews: List<NaverReview>,
    var score: NaverScoreSet?,
)
