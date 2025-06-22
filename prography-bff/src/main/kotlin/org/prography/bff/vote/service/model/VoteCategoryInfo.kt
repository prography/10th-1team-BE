package org.prography.bff.vote.service.model

import org.prography.bff.vote.repository.model.enumeration.VoteCategory

/**
 * 득표 범주 정보
 */
data class VoteCategoryInfo(
    /**
     * 득표 유형
     */
    val category: VoteCategory,
    /**
     * 득표 수
     */
    val count: Long = 0,
)
