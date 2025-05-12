package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class MediaItem(
    val type: String,
    val thumbnail: String,
    val videoUrl: String? = null,
)
