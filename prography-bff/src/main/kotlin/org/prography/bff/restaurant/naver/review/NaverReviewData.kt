package org.prography.bff.restaurant.naver.review

data class NaverReviewData(
    var reviews: List<NaverReview>,
    var score: NaverScoreSet?,
)
