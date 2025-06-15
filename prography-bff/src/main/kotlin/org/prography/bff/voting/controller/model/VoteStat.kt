package org.prography.bff.voting.controller.model

import org.prography.bff.voting.controller.model.enumeration.VoteReason

/**
 * 이유별 투표 상태
 */
data class VoteStat(
    /**
     * 투표 이유
     */
    val reason: VoteReason,
    /**
     * 투표 수
     */
    val count: Long,
    /**
     * 투표 비율
     */
    val ratio: Double,
)
