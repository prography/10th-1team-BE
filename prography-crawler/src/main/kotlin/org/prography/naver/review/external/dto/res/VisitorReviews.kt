package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * GraphQL 응답의 `visitorReviews` 필드 매핑
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class VisitorReviews(
    var total: Int = 0,
    var starDistribution: List<StarDistribution>? = null,
    var items: List<ReviewItem> = emptyList(),
)
