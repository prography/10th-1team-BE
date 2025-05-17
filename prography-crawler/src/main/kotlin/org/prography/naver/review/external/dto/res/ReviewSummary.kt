package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReviewSummary(
    @JsonProperty("avgRating")
    val avgRating: Double? = null,
    val totalCount: Int? = null,
)
