package org.prography.bff.vote.service.model.composite

import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.time.LocalDateTime

/**
 * 투표 이력에 대한 정보
 */
data class PlatformVoteHistory(
    /**
     * 이력의 고유 아이디
     */
    val id: Long,
    /**
     * 투표한 플랫폼
     */
    val platform: VotePlatform,
    /**
     * 플랫폼에 투표한 이유
     */
    val reasons: List<VoteCategory>,
    /**
     * 투표한 시점
     */
    val votedDate: LocalDateTime,
)
