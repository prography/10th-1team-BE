package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class VotedKeyword(
    val totalCount: Int? = null,
    val reviewCount: Int? = null,
    val userCount: Int? = null,
    val details: List<VotedKeywordDetail>? = null,
)
