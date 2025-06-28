package org.prography.bff.vote.service.model

data class VoteSummary(
    val total: Long,
    val isUserVoted: Boolean,
)
