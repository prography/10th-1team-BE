package org.prography.bff.search.model.strength

import io.swagger.v3.oas.annotations.media.Schema

data class StrengthRateDto(
    @Schema(example = "맛") val description: String,
    @Schema(example = "0.866") val kakaoRate: Double,
    @Schema(example = "0.81") val naverRate: Double,
)
