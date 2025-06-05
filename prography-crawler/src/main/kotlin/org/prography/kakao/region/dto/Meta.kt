package org.prography.kakao.region.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Meta(
    @JsonProperty("total_count")
    val totalCount: Int = 0,
)
