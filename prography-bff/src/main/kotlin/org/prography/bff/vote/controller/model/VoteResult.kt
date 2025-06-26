package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform

/**
 * 투표 결과
 */
@Schema(description = "투표 결과")
data class VoteResult(
    /**
     * 플랫폼
     */
    @Schema(description = "투표한 플랫폼")
    val platform: MatchPlatform,
    /**
     * 투표 수
     */
    @Schema(description = "해당 플랫폼의 득표 수")
    val count: Long,
    /**
     * 이유 별 투표 현황
     */
    @Schema(description = "이유별 득표 결과")
    val reasons: List<VoteStat>,
)
