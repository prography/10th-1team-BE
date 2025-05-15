package org.prography.kakao.review.service

import org.prography.kakao.review.external.converter.KakaoReviewDataConverter
import org.prography.kakao.review.external.dto.KakaoReviewResponse
import org.prography.kakao.review.external.dto.Order
import org.prography.kakao.review.external.feign.KakaoReviewFeignClient
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class KakaoReviewService(
    private val kakaoReviewClient: KakaoReviewFeignClient,
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
) {
    companion object {
        private val log = LoggerFactory.getLogger(KakaoReviewService::class.java)
    }

    fun saveKakaoReview(restaurant: RawRestaurantData) {
        val kakaoId =
            restaurant.kakaoPlaceData?.id
                ?: throw IllegalStateException("kakaoPlaceData is null for ${restaurant.id}")

        // 리뷰 페이징 호출 → 전체 합산
        val response = searchReviewsByKakaoId(kakaoId)
        restaurant.kakaoReviewData = KakaoReviewDataConverter.toDomain(response)
        restaurant.kakaoReviewProcessed = true
        rawRestaurantDataRepository.save(restaurant)
    }

    /**
     * 카카오 아이디로 리뷰를 페이징 조회하여 모두 합칩니다.
     */
    private fun searchReviewsByKakaoId(kakaoId: String): KakaoReviewResponse {
        var previousLastReviewId = 0L
        val kakaoReviewResponse: KakaoReviewResponse =
            kakaoReviewClient.searchReviewsByKakaoId(
                previousLastReviewId,
                Order.LATEST,
                false,
                kakaoId,
            )
        if (!kakaoReviewResponse.reviews.isNullOrEmpty() && kakaoReviewResponse.hasNext) {
            previousLastReviewId = kakaoReviewResponse.reviews.last().reviewId
            val nextReviewResponse =
                kakaoReviewClient.searchReviewsByKakaoId(
                    previousLastReviewId,
                    Order.LATEST,
                    false,
                    kakaoId,
                )
            if (!nextReviewResponse.reviews.isNullOrEmpty()) {
                kakaoReviewResponse.reviews.addAll(nextReviewResponse.reviews)
            }
        }
        log.info("Review size : {}", kakaoReviewResponse.reviews!!.size)
        return kakaoReviewResponse
    }
}
