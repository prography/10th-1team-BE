package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Review(
    @JsonProperty("review_id") val reviewId: Long,
    @JsonProperty("star_rating") val starRating: Int,
    val contents: String? = null,
    @JsonProperty("photo_count") val photoCount: Int,
    val photos: List<Photo>? = null,
    val status: String? = null,
    @JsonProperty("strength_ids") val strengthIds: List<Int>? = null,
    @JsonProperty("registered_at") val registeredAt: String,
    @JsonProperty("updated_at") val updatedAt: String,
    val meta: Meta,
)
