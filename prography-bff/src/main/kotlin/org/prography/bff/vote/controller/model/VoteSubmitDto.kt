package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason

/**
 * 플랫폼 투표 DTO
 */
@Schema(description = "플랫폼 투표 요청")
data class VoteSubmitDto(
    /**
     * 투표한 플랫폼
     */
    @Schema(description = "투표한 플랫폼", example = "KAKAO", required = true)
    val platform: MatchPlatform,
    /**
     * 투표된 이유
     */
    @Schema(description = "플랫폼 투표 이유")
    val reasons: List<Reason> = emptyList(),
)
