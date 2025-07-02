package org.prography.bff.user.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

/**
 * 가게를 저장하고 있는 굷
 */
@Schema(title = "유저가 가지고 있는 그룹", description = "유저가 가지고 있는 그룹")
data class BookmarkGroup(
    /**
     * 그룹 아이디
     */
    @Schema(title = "그룹 아이디", description = "유저가 소유한 그룹 아이디")
    val groupId: UUID,
    /**
     * 그룹 이름
     */
    @Schema(title = "그룹 이름", description = "유저가 소유한 그룹의 이름")
    val groupName: String,
    /**
     * 지정된 아이콘
     */
    @Schema(title = "그룹 아이콘", description = "그룹 생성 시 선택한 아이콘")
    val icon: String,
    /**
     * 그룹에 저장된 가개의 수
     */
    @Schema(title = "저장된 가게 수", description = "그룹에 저장되어 있는 가게의 총합")
    val savedOfNum: Int,
    /**
     * 그룹이 생성된 날짜
     */
    @Schema(title = "생성 날짜", description = "그룹을 처음 생성한 날짜")
    val createdDate: LocalDateTime,
    /**
     * 그룹에 가게가 저장된 날짜
     */
    @Schema(title = "저장 날짜", description = "그룹에 가게를 마지막에 저장한 날짜")
    val savedDate: LocalDateTime,
)
