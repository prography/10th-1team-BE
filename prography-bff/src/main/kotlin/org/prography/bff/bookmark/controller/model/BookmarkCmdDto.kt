package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

/**
 * 북마크 생성 및 수정을 위한 DTO
 */
@Schema(title = "북마크 명령 DTO", description = "북마크 생성 및 수정을 위한 DTO")
data class BookmarkCmdDto(
    /**
     * 명령할 가게 아이디
     */
    @Schema(title = "가게 아이디", description = "명령을 수행할 가게 아이디")
    val placeId: List<String>,
    /**
     * 그룹 아이디 리스트
     */
    @Schema(title = "그룹 아이디 목록", description = "해당 명령을 수행할 그룹 아이디 목록")
    val groupIds: List<UUID>,
)
