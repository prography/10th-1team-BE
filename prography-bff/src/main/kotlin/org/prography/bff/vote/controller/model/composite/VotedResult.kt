package org.prography.bff.vote.controller.model.composite

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.Reason

/**
 * 득표 정보
 */
@Schema(title = "득표 결과", description = "득표된 결과")
data class VotedResult(
    /**
     * 투표 수
     */
    @Schema(title = "플랫폼 득표 수", description = "해당 플랫폼의 득표 수")
    val count: Long = 0L,
    /**
     * 이유 별 투표 현황
     */
    @Schema(title = "이유별 득표 수", description = "이유별 득표 수")
    val reasons: Map<Reason, Long>,
)
