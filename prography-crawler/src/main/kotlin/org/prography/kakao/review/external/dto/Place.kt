package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Place(
    @JsonProperty("confirm_id") val id: String,
    @JsonProperty("place_name") val name: String,
)
