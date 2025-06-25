package org.prography.bff.vote.controller.model

/**
 * 플랫폼 투표 상태
 */
data class PlatformVoteResultDto(
    /**
     * 투표 결과 유무
     */
    val voted: Boolean,
    /**
     * 투표 결과 데이터
     */
    val results: List<VoteResult>,
)
