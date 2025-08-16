package org.prography.bff.bookmark.controller.model.roulette

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(title = "룰렛", description = "저장된 룰렛")
data class RouletteGroup(
    @Schema(title = "아이디", description = "해당 룰렛의 고유 아이디")
    val id: UUID,
    @Schema(title = "이름", description = "해당 룰렛의 이름")
    val name: String,
    @Schema(title = "아이콘", description = "해당 룰렛의 아이콘")
    val icon: String,
    @Schema(title = "저장된 갯수", description = "해당 룰렛이 가지고 있는 가게의 갯수")
    val numberOfItem: Long,
    @Schema(title = "저장 유무", description = "해당 가게가 본 룰렛에 저장되어 있는 유무")
    val isAdded: Boolean = false,
    @Schema(title = "생성 날짜", description = "해당 룰렛이 생성된 날짜")
    val createAt: LocalDateTime,
    @Schema(title = "추가 날짜", description = "룰렛에 가게가 추가된 마지막 날짜")
    val savedAt: LocalDateTime,
)
