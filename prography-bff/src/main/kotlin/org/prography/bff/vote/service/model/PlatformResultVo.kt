package org.prography.bff.vote.service.model

import org.prography.bff.vote.service.model.composite.PlatformVoteHistory
import org.prography.bff.vote.service.model.composite.PlatformVoteInfo

/**
 * 투표 결과
 */
data class PlatformResultVo(
    /**
     * 투표 이력 존재 유무
     */
    val voted: Boolean = false,
    /**
     * 플랫폼 투표 이력
     */
    val history: PlatformVoteHistory?,
    /**
     * 플랫폼 투표 정보
     */
    val result: List<PlatformVoteInfo> = emptyList(),
)
