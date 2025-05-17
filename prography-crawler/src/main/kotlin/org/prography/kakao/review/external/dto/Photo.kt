package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Photo(
    val url: String,
    @JsonProperty("photo_id") val photoId: Long,
    @JsonProperty("review_id") val reviewId: Long,
    @JsonProperty("updated_at") val updatedAt: String,
    val meta: PhotoMeta,
)
