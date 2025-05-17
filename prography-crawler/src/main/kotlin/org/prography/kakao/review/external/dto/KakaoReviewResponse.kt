package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoReviewResponse(
    @JsonProperty("score_set") val scoreSet: ScoreSet? = null,
//    @JsonProperty("strength_description") val strengthDesc: List<StrengthDesc>,
    val reviews: MutableList<Review>? = mutableListOf(),
    @JsonProperty("timeline_score_table") val timelineScoreTable: TimelineScoreTable? = null,
    @JsonProperty("has_next") val hasNext: Boolean,
)
