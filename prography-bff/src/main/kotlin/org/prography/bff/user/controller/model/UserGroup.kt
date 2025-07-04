package org.prography.bff.user.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

/**
 * 유저가 생성한 그룹에 대한 정보
 */
@Schema(title = "그룹", description = "해당 유저가 저장한 그룹에 대한 내역 정보")
data class UserGroup(
    /**
     * 저장된 그룹 아이디
     */
    @Schema(title = "그룹 아이디", description = "그룹 고유 아이디")
    val groupId: UUID,
    /**
     *
     */
    @Schema(title = "아이콘", description = "설정한 아이콘 값")
    val icon: String,
    /**
     * 저장한 가게의 이름
     */
    @Schema(title = "그룹 이름", description = "저장된 그룹의 이름")
    val groupName: String,
    /**
     * 저장된 가게의 수
     */
    @Schema(title = "총합", description = "저장된 가게의 수")
    val total: Long,
    /**
     * 그룹을 생성한 날짜
     */
    @Schema(title = "생성 날짜", description = "그룹을 생성한 날짜")
    val createdDate: LocalDateTime,
    /**
     * 저장한 날짜
     */
    @Schema(title = "저장 날짜", description = "가게를 그룹에 저장한 날짜")
    val savedDate: LocalDateTime,
)
