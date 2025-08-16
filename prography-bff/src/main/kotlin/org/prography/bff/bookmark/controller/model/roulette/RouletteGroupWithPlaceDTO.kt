package org.prography.bff.bookmark.controller.model.roulette

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.bookmark.controller.model.BookmarkPlace
import java.util.UUID

@Schema(title = "룰렛 정보와 추가된 가게 조회 DTO", description = "룰렛과 추가된 가게에 대한 정보 함께 조회")
data class RouletteGroupWithPlaceDTO(
    @Schema(title = "룰렛 아이디", description = "룰렛 고유 아이디")
    val id: UUID,
    @Schema(title = "룰렛 이름", description = "룰렛의 이름")
    val name: String,
    @Schema(title = "룰렛 아이콘", description = "룰렛에 지정된 아이콘")
    val icon: String,
    @Schema(title = "저장된 가게 수", description = "룰렛에 추가된 가게의 수")
    val total: Long,
    @Schema(title = "추가된 가게", description = "해당 룰렛에 추가된 가게의 정보")
    val places: List<BookmarkPlace>,
)
