package org.prography.bff.voting.controller.model

import org.prography.bff.voting.controller.model.enumeration.MatchPlatform
import org.prography.bff.voting.controller.model.enumeration.VoteReason

/**
 * 플랫폼 투표 DTO
 */
data class PlatformVoteDto(
    /**
     * 투표한 플랫폼
     */
    val platform: MatchPlatform,
    /**
     * 투표된 이유
     */
    val reason: VoteReason,
    /**
     * 음식점에 대한 후기
     */
    val restaurantReview: String = "",
)
