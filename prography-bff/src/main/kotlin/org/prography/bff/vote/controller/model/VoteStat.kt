package org.prography.bff.vote.controller.model

import org.prography.bff.vote.controller.model.enumeration.Reason

/**
 * 이유별 투표 상태
 */
data class VoteStat(
    /**
     * 투표 이유
     */
    val reason: Reason,
    /**
     * 투표 수
     */
    val count: Long,
)
