package org.prography.bff.vote.service.model

import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.util.UUID

/**
 * 투표 하기 위한 정보를 담은 객체
 */
data class SubmitVo(
    /**
     * 투표한 유저의 아이디
     */
    val userId: UUID,
    /**
     * 투표한 플랫폼
     */
    val platform: VotePlatform,
    /**
     * 투표의 유형
     */
    val categories: List<VoteCategory>,
)
