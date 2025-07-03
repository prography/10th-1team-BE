package org.prography.bff.vote.controller.model.composite

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason
import java.time.LocalDateTime

/**
 * 유저가 투표한 결과 기록
 */
@Schema(title = "투표 기록", description = "투표 결과 기록")
data class VoteRecord(
    /**
     * 플랫폼
     */
    @Schema(title = "플랫폼", description = "투표된 플랫폼")
    val platform: MatchPlatform,
    /**
     * 유저가 투표한 이유
     */
    @Schema(title = "이유", description = "투표한 이유")
    val reason: List<Reason> = emptyList(),
    /**
     * 유저가 투표한 날짜
     */
    @Schema(title = "날짜", description = "투표한 날짜")
    val votedDate: LocalDateTime,
)
