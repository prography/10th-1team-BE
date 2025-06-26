package org.prography.bff.vote.service.model

import org.prography.bff.vote.repository.model.enumeration.VotePlatform

/**
 * 득표 정보
 */
data class VoteInfo(
    /**
     * 투표 플랫폼
     */
    val platform: VotePlatform,
    /**
     * 총 득표 수
     */
    val total: Long = 0L,
    /**
     * 득표 유형
     */
    val categories: List<VoteCategoryInfo>,
)
