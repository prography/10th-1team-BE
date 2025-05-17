package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActReview(
    val event: Int,
    @JsonProperty("place_banner") val placeBanner: List<Banner> = emptyList(),
)
