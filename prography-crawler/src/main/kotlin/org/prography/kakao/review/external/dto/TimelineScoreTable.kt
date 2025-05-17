package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TimelineScoreTable(
    @JsonProperty("act_review") val actReview: ActReview?,
)
