package org.prography.restaurant.naver.review

data class NaverScoreSet(
    var reviewRating: Double?,
    var totalCount: Int,
    var votedTotalCount: Int,
    var votedReviewCount: Int,
    var strengthCounts: List<NaverStrengthCount>,
)
