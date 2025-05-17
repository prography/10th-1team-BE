package org.prography.kakao.review.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Owner(
    @JsonProperty("map_user_id") val id: String,
    val nickname: String? = null,
    @JsonProperty("profile_image_url")
    val profileImageUrl: String? = null,
    @JsonProperty("timeline_level")
    val timelineLevel: TimelineLevel? = null,
    @JsonProperty("review_count")
    val reviewCount: Int? = null,
    @JsonProperty("follower_count")
    val followerCount: Int? = null,
    @JsonProperty("average_score")
    val averageScore: Double? = null,
    @JsonProperty("profile_status")
    val profileStatus: String? = null,
    @JsonProperty("is_following")
    val isFollowing: Boolean? = null,
)
