package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class GraphQLData(
    val visitorReviews: VisitorReviews? = null,
    val visitorReviewStats: VisitorReviewStats? = null,
)
