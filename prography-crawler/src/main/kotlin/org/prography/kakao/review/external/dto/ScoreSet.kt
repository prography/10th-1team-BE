package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ScoreSet(
    @JsonProperty("review_count") val reviewCount: Int,
    @JsonProperty("total_score") val totalScore: Int,
    @JsonProperty("average_score") val averageScore: Double,
    @JsonProperty("strength_counts") val strengthCounts: List<StrengthCount>,
    @JsonProperty("strength_uv") val strengthUv: Int,
)
