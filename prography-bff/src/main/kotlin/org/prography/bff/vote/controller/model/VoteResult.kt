package org.prography.bff.vote.controller.model

import org.prography.bff.vote.controller.model.enumeration.MatchPlatform

/**
 * 투표 결과
 */
data class VoteResult(
    /**
     * 플랫폼
     */
    val platform: MatchPlatform,
    /**
     * 투표 수
     */
    val count: Long,
    /**
     * 이유 별 투표 현황
     */
    val reasons: List<VoteStat>,
)
