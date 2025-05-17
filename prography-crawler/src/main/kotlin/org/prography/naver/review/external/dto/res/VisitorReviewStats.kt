package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/** ──────────────── visitorReviewStats ──────────────── */
@JsonIgnoreProperties(ignoreUnknown = true)
data class VisitorReviewStats(
    val id: String? = null,
    val review: ReviewSummary? = null,
    val analysis: Analysis? = null,
)
