package org.prography.bff.vote.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.composite.VoteRecord
import org.prography.bff.vote.controller.model.composite.VotedResult
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform

/**
 * 플랫폼 투표 상태
 */
@Schema(title = "투표 결과", description = "투표 상태")
data class VoteResultDto(
    /**
     * 투표 총합
     */
    @Schema(title = "투표 총합", description = "투표 참여자 수 총합")
    val total: Long = 0L,
    /**
     * 유저 투표 여부
     */
    @Schema(title = "유저의 투표 유무", description = "해당 유저의 투표 유무")
    val voted: Boolean = false,
    /**
     * 유저 투표 기록
     */
    @Schema(title = "투표 기록", description = "해당 유저의 투표 기록")
    val record: VoteRecord?,
    /**
     * 투표 결과 데이터
     */
    @Schema(title = "결과", description = "투표에 대한 결과")
    val results: Map<MatchPlatform, VotedResult>,
)
