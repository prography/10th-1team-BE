package org.prography.bff.voting.service.model

import org.prography.bff.voting.service.model.enumeration.VoteCategory
import org.prography.bff.voting.service.model.enumeration.VotePlatform

/**
 * 투표 하기 위한 정보를 담은 객체
 */
class VoteSubmit(
    private val platform: VotePlatform,
    private val reason: VoteCategory,
    private val review: String = "",
)
