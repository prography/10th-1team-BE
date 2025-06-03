package org.prography.bff.restaurant.naver.review

data class NaverMedia(
    var type: String,
    var thumbnail: String,
    var videoUrl: String? = null,
)
