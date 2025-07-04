package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 플랫폼 투표 요약
 */
@Schema(title = "투표 요약", description = "플랫폼 투표 요약")
data class VoteSummaryDto(
    /**
     * 투표 참여자 수
     */
    @Schema(title = "총 참여자 수", description = "음식점 투표에 참여한 유저의 수")
    val total: Long,
    /**
     * 유저 투표 여부
     */
    @Schema(title = "투표 유무", description = "해당 음식점에 유저가 투표한 유무")
    val voted: Boolean = false,
)
