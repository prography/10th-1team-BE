package org.prography.search.controller.model

/**
 *
 */
data class SearchResponseDTO(
    val id: String,
    val addresses: String,
    val roadAddresses: String,
    val category: String?,
    val name: String,
    val imageUrl: String?,
    val kakao: ReviewSummary,
    val naver: ReviewSummary,
)
