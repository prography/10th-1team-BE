package org.prography.kakao.review.service

import org.prography.kakao.review.external.converter.KakaoReviewDataConverter
import org.prography.kakao.review.external.dto.KakaoReviewResponse
import org.prography.kakao.review.external.dto.Order
import org.prography.kakao.review.external.feign.KakaoReviewFeignClient
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class KakaoReviewService(
    @Qualifier("kakaoScrapExecutor")
    private val executor: ThreadPoolTaskExecutor,
    private val kakaoReviewClient: KakaoReviewFeignClient,
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
) {
    companion object {
        private const val SLEEP_MS = 5_000L
        private val log = LoggerFactory.getLogger(KakaoReviewService::class.java)
    }

    fun updateAllReviewsAsync(restaurants: List<RawRestaurantData>) {
        val futures = restaurants.map(::processRestaurantAsync)
        futures.awaitAll()

        val failures = futures.count { it.isCompletedExceptionally }
        log.info(
            "✅ All review updates completed. successes={} failures={}",
            restaurants.size - failures,
            failures,
        )
    }

    private fun processRestaurantAsync(data: RawRestaurantData): CompletableFuture<Void> =
        CompletableFuture.runAsync({
            runCatching {
                Thread.sleep(SLEEP_MS) // TODO: 네트워크 쿨다운이라면 retry/backoff 로 교체 고려
                val kakaoId =
                    data.kakaoPlaceData?.id
                        ?: throw IllegalStateException("kakaoPlaceData is null for ${data.id}")

                // 리뷰 페이징 호출 → 전체 합산
                val response = searchReviewsByKakaoId(kakaoId)
                data.kakaoReviewData = KakaoReviewDataConverter.toDomain(response)
                rawRestaurantDataRepository.save(data)
            }.onFailure { ex ->
                log.error(
                    "❌ Failed to process review for id={}, kakaoID = {}",
                    data.id,
                    data.kakaoPlaceData?.id,
                    ex,
                )
            }
        }, executor)

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

private fun <T> List<CompletableFuture<T>>.awaitAll(): Void? = CompletableFuture.allOf(*toTypedArray()).join()
