package org.prography.bff.bookmark.controller.model.roulette

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.bookmark.controller.model.BookmarkPlace
import java.util.UUID

@Schema(title = "저장된 가게 조회 DTO", description = "그룹에 대한 정보과 가게에 정보를 조회")
data class RouletteItemDTO(
    /**
     * 해당 그룹에 지정된 그룹 아이디
     */
    @Schema(title = "그룹 아이디", description = "그룹 고유 아이디")
    val groupId: UUID,
    /**
     * 그룹에 대한 이름
     */
    @Schema(title = "그룹 이름", description = "그룹의 이름")
    val groupName: String,
    /**
     * 그룹에 지정된 아이콘
     */
    @Schema(title = "그룹 아이콘", description = "그룹에 지정된 아이콘")
    val icon: String,
    /**
     * 그룹에 저장된 가게의 수
     */
    @Schema(title = "저장된 가게 수", description = "그룹에 저장된 가게의 수")
    val total: Long,
    /**
     * 해당 그룹에 저장된 가게들의 정보 리스트
     */
    @Schema(title = "저장된 가게", description = "해당 그룹에 저장된 가게의 정보")
    val places: List<BookmarkPlace>,
)
