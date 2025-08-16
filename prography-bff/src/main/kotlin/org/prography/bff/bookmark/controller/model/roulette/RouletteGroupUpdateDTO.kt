package org.prography.bff.bookmark.controller.model.roulette

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "룰렛 그룹 수정 DTO", description = "룰렛 그룹 수정 DTO")
data class RouletteGroupUpdateDTO(
    @Schema(title = "이름", description = "수정할 룰렛 이름")
    val name: String,
    @Schema(title = "아이콘", description = "수정할 룰렛 아이콘")
    val icon: String,
)
