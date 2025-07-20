package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * GET /bookmark/group
 */
@Schema(title = "그룹의 정보", description = "유저가 생성한 그룹의 정보")
data class BookmarkGroupsDTO(
    /**
     *
     */
    @Schema(title = "그룹의 총 갯수", description = "유저가 생성한 그룹의 총 갯수")
    val total: Long = 0L,
    /**
     *
     */
    @Schema(title = "그룹 정보 목록", description = "유저가 생성한 그룹의 정보")
    val groups: List<BookmarkGroup> = emptyList(),
)
