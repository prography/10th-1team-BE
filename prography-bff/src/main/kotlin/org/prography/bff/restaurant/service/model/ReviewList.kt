package org.prography.bff.restaurant.service.model

import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.RawRestaurantData
import org.prography.bff.restaurant.controller.model.review.ReviewDto

data class ReviewList(
    val name: String,
    val roadAddressName: String,
    val totalCount: Int,
    val kakaoReviewCount: Int,
    val naverReviewCount: Int,
    val reviews: List<ReviewDto>,
) {
    companion object {
        fun fromDomain(restaurantData: RawRestaurantData): ReviewList {
            val kakaoPlaceInfo =
                restaurantData.kakaoPlaceData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()
            val kakaoReviewData =
                restaurantData.kakaoReviewData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()
            val naverReviewData =
                restaurantData.naverReviewData
                    ?: throw NotFoundException.PlaceInfoNotFoundException()

            val naverReviewScore =
                naverReviewData.score ?: throw NotFoundException.PlaceInfoNotFoundException()

            val kakaoReviews = kakaoReviewData.reviews.map(ReviewDto::fromKakaoReview)
            val naverReviews = naverReviewData.reviews.map(ReviewDto::fromNaverReview)

            val allReviews = (kakaoReviews + naverReviews).sortedByDescending { it.registeredAt }

            return ReviewList(
                name = kakaoPlaceInfo.placeName,
                roadAddressName = kakaoPlaceInfo.roadAddressName,
                totalCount = kakaoReviewData.score.reviewCount + naverReviewScore.totalCount,
                kakaoReviewCount = kakaoReviewData.score.reviewCount,
                naverReviewCount = naverReviewScore.totalCount,
                reviews = allReviews,
            )
        }
    }
}
