package org.prography.bff.vote.service.model

import org.prography.bff.vote.repository.model.enumeration.VotePlatform

data class VoteResult(
    val voted: Boolean = false,
    val voteRecord:
    val infoMap: Map<VotePlatform, VoteInfo> = emptyMap(),
)
