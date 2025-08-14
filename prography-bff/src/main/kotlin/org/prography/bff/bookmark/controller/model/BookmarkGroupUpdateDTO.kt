package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * PATCH /bookmark/group/{groupId}
 * 저장 그룹 수정하기 DTO
 */
@Schema(title = "수정 DTO", description = "그룹 수정 DTO")
data class BookmarkGroupUpdateDTO(
    /**
     * 수정할 그룹 이름
     */
    @Schema(title = "그룹 이름", description = "생성할 저장 그룹 이름")
    val groupName: String,
    /**
     * 수정할 아이콘
     */
    @Schema(title = "아이콘", description = "생성할 그룹의 아이콘")
    val icon: String,
)
