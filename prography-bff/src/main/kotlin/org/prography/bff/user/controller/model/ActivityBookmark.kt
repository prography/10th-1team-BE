package org.prography.bff.user.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

/**
 * 저장 활동 내역
 */
@Schema(title = "저장 활동 내역", description = "해당 유저가 저장한 활동에 대한 내역 정보")
data class ActivityBookmark(
    /**
     * 저장된 그룹 아이디
     */
    @Schema(title = "그룹 아이디", description = "가게가 저장된 그룹 아이디")
    val groupId: UUID,
    /**
     * 저장한 가게의 이름
     */
    @Schema(title = "가게 이름", description = "저장한 가게의 상호명")
    val placeName: String,
    /**
     * 저장한 날짜
     */
    @Schema(title = "저장 날짜", description = "가게를 그룹에 저장한 날짜")
    val savedDate: LocalDateTime,
)
