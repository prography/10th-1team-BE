package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

/**
 * 저장 그룹에 대한 조회 DTO
 */
@Schema(title = "조회된 그룹 DTO", description = "저장 및 수정에서 출력된 그룹 DTO")
data class BookmarkGroupInfoDto(
    /**
     * 해당 그룹의 고유 아이디
     */
    @Schema(title = "그룹 아이디", description = "해당 그룹의 고유 아이디")
    val groupId: UUID,
    /**
     * 해당 그룹의 이름
     */
    @Schema(title = "그룹 이름", description = "해당 그룹의 이름")
    val groupName: String,
    /**
     * 해당 그룹의 아이콘
     */
    @Schema(title = "아이콘", description = "해당 그룹의 아이콘")
    val icon: String,
    /**
     * 해당 그룹이 가지고 있는 가게의 갯수
     */
    @Schema(title = "저장된 갯수", description = "해당 그룹이 가지고 있는 가게의 갯수")
    val numberOfBookmark: Long,
    /**
     * 해당 가게가 본 그룹에 저장되어 있는 유무
     */
    @Schema(title = "저장 유무", description = "해당 가게가 본 그룹에 저장되어 있는 유무")
    val isSaved: Boolean = false,
    /**
     * 그룹이 생성된 날짜
     */
    @Schema(title = "생성 날짜", description = "해당 그룹이 생성된 날짜")
    val createAt: LocalDateTime,
    /**
     * 그룹에 가게가 추가된 날짜
     */
    @Schema(title = "추가 날짜", description = "그룹에 가게가 추가된 마지막 날짜")
    val savedAt: LocalDateTime,
)
