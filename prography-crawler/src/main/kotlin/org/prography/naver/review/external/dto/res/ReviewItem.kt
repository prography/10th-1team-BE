package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ReviewItem(
    val id: String,
    val reviewId: String,
    val originType: String? = null,
    val rating: Double? = null,
    val body: String? = null,
    val created: String,
    val media: List<MediaItem>? = null,
    val author: Author,
    val visitCount: Int? = null,
)
