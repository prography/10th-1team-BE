package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 플랫폼 투표 상태
 */
@Schema(description = "투표 상태")
data class PlatformVoteResultDto(
    /**
     * 투표 결과 유무
     */
    @Schema(description = "투표 결과가 있는지 유무")
    val voted: Boolean,
    /**
     * 투표 결과 데이터
     */
    @Schema(description = "투표에 대한 결과")
    val results: List<VoteResult>,
)
