package org.prography.naver.place.domain

data class NaverPlaceInfo(
    var id: String,
    var name: String,
    var category: List<String>,
    var address: String,
    var roadAddress: String,
    var shortAddress: List<String>,
    var x: Double?,
    var y: Double?,
    var thumUrl: String?,
    var thumUrls: List<String>,
    var reviewCount: Int,
    var placeReviewCount: Int,
    var microReview: List<String>,
    var menuInfo: String?,
    var context: List<String>,
)
