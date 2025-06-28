package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class VoteSummaryDto(
    @Schema(description = "음식점 투표 전체 수")
    val total: Long,
    /**
     * 유저 상세 투표 여부
     */
    @Schema(description = "유저 음식점 투표 여부")
    val isUserVoted: Boolean = false,
)
