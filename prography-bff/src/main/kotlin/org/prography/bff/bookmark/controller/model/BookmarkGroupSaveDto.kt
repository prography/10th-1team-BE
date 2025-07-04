package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 저장 그룹 생성 DTO
 */
@Schema(title = "저장 DTO", description = "저장 그룹 생성 DTO")
data class BookmarkGroupSaveDto(
    /**
     * 생성할 저장 그룹 이름
     */
    @Schema(title = "그룹 이름", description = "생성할 저장 그룹 이름")
    val groupName: String,
    /**
     * 생성할 그룹의 아이콘
     */
    @Schema(title = "아이콘", description = "생성할 그룹의 아이콘")
    val icon: String,
)
