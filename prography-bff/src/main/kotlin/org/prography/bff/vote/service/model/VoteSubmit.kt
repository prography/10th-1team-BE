package org.prography.bff.vote.service.model

import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.util.UUID

/**
 * 투표 하기 위한 정보를 담은 객체
 */
data class VoteSubmit(
    val userId: UUID,
    val platform: VotePlatform,
    val reason: List<VoteCategory>,
)
