package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class TimelineLevel(
    val badge: String,
    @JsonProperty("now_level") val nowLevel: Int,
)
