package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.Reason

/**
 * 이유별 투표 상태
 */
@Schema(description = "이유별 득표 결과")
data class VoteStat(
    /**
     * 투표 이유
     */
    @Schema(description = "득표 이유")
    val reason: Reason,
    /**
     * 투표 수
     */
    @Schema(description = "득표 수")
    val count: Long,
)
