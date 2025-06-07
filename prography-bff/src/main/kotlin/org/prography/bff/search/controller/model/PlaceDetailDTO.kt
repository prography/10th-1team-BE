package org.prography.bff.search.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.search.model.photo.Photo
import org.prography.bff.search.model.reveiw.Review
import org.prography.bff.search.model.strength.StrengthScoresDto

data class PlaceDetailDTO(
    @Schema(example = "14601427") val kakaoPlaceUri: String,
    @Schema(example = "1720070048") val naverPlaceUri: String,
    @Schema(example = "카이센동우니도 신사본점") val name: String,
    @Schema(example = "서울 강남구 신사동 522") val addressName: String,
    @Schema(example = "서울 강남구 압구정로2길 15") val roadAddressName: String,
    @Schema(example = "1165010600") val bCode: String,
    @Schema(example = "127.01766434132446", description = "x좌표") val x: String,
    @Schema(example = "37.52770253861908", description = "y좌표") val y: String,
    val photos: List<Photo>,
    val strengthScoresDto: StrengthScoresDto,
    @Schema(example = "53", description = "카카오 리뷰 수") val kakaoReviewCount: Int,
    @Schema(example = "4.1", description = "카카오 별점 평균") val kakaoReviewAvgScore: Double,
    val kakaoReviews: List<Review>,
    @Schema(example = "4.1", description = "카카오 별점 투표 수") val kakaoVoteRate: Int,
    @Schema(example = "353", description = "네이버 리뷰 수") val naverReviewCount: Int,
    @Schema(example = "4.14", description = "카카오 별점 평균") val naverReviewAvgScore: Double? = null,
    val naverReviews: List<Review>,
    @Schema(example = "4.1", description = "네이버 별점 투표 수") val naverVoteRate: Int,
)
