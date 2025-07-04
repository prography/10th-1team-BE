package org.prography.bff.vote.service.model.composite

import org.prography.bff.vote.repository.model.enumeration.VoteCategory

/**
 * 득표 상태
 */
data class VotingStats(
    /**
     * 득표 범주
     */
    val category: VoteCategory,
    /**
     * 득표 수
     */
    val count: Long = 0,
)
