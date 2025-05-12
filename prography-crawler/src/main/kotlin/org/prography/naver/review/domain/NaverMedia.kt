package org.prography.naver.review.domain

data class NaverMedia(
    var type: String,
    var thumbnail: String,
    var videoUrl: String? = null,
)
