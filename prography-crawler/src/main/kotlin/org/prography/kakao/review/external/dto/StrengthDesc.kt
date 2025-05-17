package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class StrengthDesc(
    val id: Int,
    val name: String,
    @JsonProperty("icon_url") val iconUrl: String,
)
