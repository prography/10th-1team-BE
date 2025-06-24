package org.prography.bff.vote.controller.model

import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason

/**
 * 플랫폼 투표 DTO
 */
data class PlatformVoteSubmitDto(
    /**
     * 투표한 플랫폼
     */
    val platform: MatchPlatform,
    /**
     * 투표된 이유
     */
    val reasons: List<Reason> = emptyList(),
)
