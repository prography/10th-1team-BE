package org.prography.bff.user.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import java.time.LocalDateTime

/**
 * 투표 활동 내역
 */
@Schema(title = "투표 활동 내역", description = "해당 유저가 투표에 대한 활동 내역 정보")
data class ActivityVote(
    /**
     * 투표한 가게의 아이디
     */
    @Schema(title = "가게 아이디", description = "유저가 투표한 가게의 아이디")
    val placeId: String,
    /**
     * 투표한 가게의 상호명
     */
    @Schema(title = "가게 이름", description = "투표한 가게의 상호명")
    val placeName: String,
    /**
     * 투표한 가게의 유형
     */
    @Schema(title = "가게 유형", description = "투표한 가게의 유형")
    val category: String,
    /**
     * 투표한 플랫폼
     */
    @Schema(title = "플랫폼", description = "해당 가게의 리뷰를 비교해서 투표한 플랫폼")
    val platform: MatchPlatform,
    /**
     * 투표한 이유
     */
    @Schema(title = "투표 이유", description = "플랫폼을 선택한 이유")
    val reasons: List<String>,
    /**
     * 투표한 날짜
     */
    @Schema(title = "투표 날짜", description = "해당 가게에 투표한 날짜")
    val votedDate: LocalDateTime,
)
