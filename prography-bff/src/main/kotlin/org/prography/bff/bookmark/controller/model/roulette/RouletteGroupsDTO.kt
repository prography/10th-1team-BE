package org.prography.bff.bookmark.controller.model.roulette

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "룰렛 그룹의 정보", description = "유저가 생성한 룰렛의 정보")
data class RouletteGroupsDTO(
    @Schema(title = "룰렛 총 갯수", description = "유저가 생성한 룰렛의 총 갯수")
    val total: Long = 0L,
    @Schema(title = "룰렛 정보 목록", description = "유저가 생성한 룰렛의 정보")
    val groups: List<RouletteGroup> = emptyList(),
)
