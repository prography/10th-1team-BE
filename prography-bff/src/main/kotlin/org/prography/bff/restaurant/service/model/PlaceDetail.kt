package org.prography.bff.restaurant.service.model

import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.RawRestaurantData
import org.prography.bff.restaurant.controller.model.Photo
import org.prography.bff.restaurant.controller.model.review.Review

data class PlaceDetail(
    val id: String?,
    val kakaoPlaceUri: String,
    val naverPlaceUri: String,
    val name: String,
    val addressName: String,
    val roadAddressName: String,
    val photos: List<Photo>,
    val strengthScores: List<StrengthScore>,
    val kakaoReviewCount: Int,
    val kakaoReviewAvgScore: Double,
    val kakaoReviews: List<Review>,
    val naverReviewCount: Int,
    val naverReviewAvgScore: Double?,
    val naverReviews: List<Review>,
    val dongName: String,
    val hCode: String,
    val bCode: String,
    val x: String,
    val y: String,
    val summaryAI: String?,
) {
    companion object {
        fun fromDomain(
            restaurantData: RawRestaurantData,
            strengthList: List<StrengthScore>,
            dongName: String,
        ): PlaceDetail {
            val kakaoPlaceInfo =
                restaurantData.kakaoPlaceData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()
            val naverPlaceInfo =
                restaurantData.naverPlaceData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()
            val kakaoReviewData =
                restaurantData.kakaoReviewData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()
            val naverReviewData =
                restaurantData.naverReviewData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()

            val naverReviewScore =
                naverReviewData.score ?: throw NotFoundException.PlaceInfoNotFoundException()

            return PlaceDetail(
                id = restaurantData.id,
                kakaoPlaceUri = kakaoPlaceInfo.id,
                naverPlaceUri = naverPlaceInfo.id,
                name = kakaoPlaceInfo.placeName,
                addressName = kakaoPlaceInfo.addressName,
                roadAddressName = kakaoPlaceInfo.roadAddressName,
                strengthScores = strengthList,
                photos = naverPlaceInfo.thumUrls.map { Photo(it) },
                kakaoReviewCount = kakaoReviewData.score.reviewCount,
                kakaoReviews = kakaoReviewData.reviews.map { Review.fromKakaoReview(it) },
                kakaoReviewAvgScore = kakaoReviewData.score.averageScore,
                naverReviewCount = naverReviewScore.totalCount,
                naverReviewAvgScore = naverReviewScore.reviewRating,
                naverReviews = naverReviewData.reviews.map { Review.fromNaverReview(it) },
                dongName = dongName,
                hCode = restaurantData.hCode,
                bCode = restaurantData.bCode,
                x = restaurantData.kakaoPlaceData.x,
                y = restaurantData.kakaoPlaceData.y,
                summaryAI = restaurantData.summaryAI,
            )
        }
    }
}
