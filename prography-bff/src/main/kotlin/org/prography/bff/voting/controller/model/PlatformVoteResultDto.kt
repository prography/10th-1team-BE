package org.prography.bff.voting.controller.model

/**
 * 플랫폼 매치에 투표 결과 확인하기 View DTO
 */
data class PlatformVoteResultDto(
    /**
     * 플랫폼 별 투표 현황
     */
    val platforms: List<PlatformStat>,
    /**
     * 이유 별 투표 현황
     */
    val reasons: List<VoteStat>,
)
