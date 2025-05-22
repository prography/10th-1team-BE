package org.prography.search.model

import io.swagger.v3.oas.annotations.media.Schema

data class PlaceSummaryDTO(
    @Schema(example = "카이센동우니도_신사본점@서울_강남구_압구정로2길_15") val id: String,
    @Schema(example = "카이센동우니도 신사본점") val name: String,
    @Schema(example = "서울 강남구 신사동 522") val addressName: String,
    @Schema(example = "서울 강남구 압구정로2길 15") val roadAddressName: String,
    @Schema(example = "93") val kakaoReviews: Int,
    @Schema(example = "3.9") val kakaoScore: Double,
    @Schema(example = "371") val naverReviews: Int,
    @Schema(example = "4.41") val naverScore: Double,
)
