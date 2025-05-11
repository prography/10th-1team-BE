package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Meta(
    val owner: Owner,
    @JsonProperty("is_liked_by_me") val likedByMe: Boolean,
    @JsonProperty("is_owner_me") val ownerMe: Boolean,
    @JsonProperty("like_count") val likeCount: Int,
    @JsonProperty("like_user_profile_image_list") val likeUserProfileImages: List<String>,
    @JsonProperty("is_place_owner_pick") val ownerPick: Boolean,
    val place: Place,
)
