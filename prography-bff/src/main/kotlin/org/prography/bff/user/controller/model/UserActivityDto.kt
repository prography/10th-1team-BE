package org.prography.bff.user.controller.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 유저 활동 데이터에 대한 조회 DTO
 */
@Schema(title = "유저 활동 데이터", description = "유저 활동 데이터(투표/저장) 관련 조회 데이터")
data class UserActivityDto(
    /**
     * 투표 활동 내역
     */
    @Schema(title = "투표 활동 내역", description = "홰당 유저의 투표 이력")
    val votes: List<ActivityVote>,
    /**
     * 저장 활동 내역
     */
    @Schema(title = "저장 활동 내역", description = "홰당 유저의 저장 이력")
    val bookmarks: List<ActivityBookmark>,
    /**
     * 저장된 그룹
     */
    @Schema(title = "저장된 그룹", description = "해당 유저 소유의 그룹 목록")
    val groups: List<BookmarkGroup>,
)
