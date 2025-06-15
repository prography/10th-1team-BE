package org.prography.bff.voting.controller.model

import org.prography.bff.voting.controller.model.enumeration.MatchPlatform

/**
 * 플랫폼 투표 상태
 */
data class PlatformStat(
    /**
     * 플랫폼
     */
    val platform: MatchPlatform,
    /**
     * 투표 수
     */
    val count: Long,
    /**
     * 투표 비율
     */
    val ratio: Double,
)
