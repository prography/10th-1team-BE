package org.prography.bff.restaurant.controller.model.review

import io.swagger.v3.oas.annotations.media.Schema

data class ReviewListDto(
    @Schema(example = "카이센동우니도 신사본점") val name: String,
    @Schema(example = "서울 강남구 압구정로2길 15") val roadAddressName: String,
    @Schema(example = "406", description = "전체 리뷰 수") val totalCount: Int,
    @Schema(example = "53", description = "카카오 전체 리뷰 수") val kakaoReviewCount: Int,
    @Schema(example = "353", description = "네이버 전체 리뷰 수") val naverReviewCount: Int,
    val reviews: List<ReviewDto>,
)
