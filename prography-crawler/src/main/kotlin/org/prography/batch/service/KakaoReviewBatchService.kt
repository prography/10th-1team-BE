package org.prography.batch.service

import org.prography.batch.domain.BatchConstants.BATCH_DELAY
import org.prography.batch.domain.BatchConstants.BATCH_SIZE
import org.prography.batch.domain.BatchConstants.LIMIT_REQUEST_TIME
import org.prography.kakao.review.service.KakaoReviewService
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class KakaoReviewBatchService(
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
    private val kakaoReviewService: KakaoReviewService,
    @Qualifier("kakaoScrapExecutor")
    private val executor: ThreadPoolTaskExecutor,
    @Qualifier("callbackExecutor")
    private val callbackExecutor: ThreadPoolTaskExecutor,
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(KakaoReviewBatchService::class.java)
    }

    // 애플리케이션 시작 후 30초 대기 → 매 30초마다 실행
    @Scheduled(initialDelay = BATCH_DELAY, fixedDelay = BATCH_DELAY)
    fun scrapKakaoReview() {
        if (isQueueFull()) {
            return
        }

        val unprocessedCount = rawRestaurantDataRepository.countByKakaoReviewProcessedFalse()
        log.info("Unchecked KakaoReviewSize: $unprocessedCount")
        if (unprocessedCount >= BATCH_SIZE) {
            val batch =
                rawRestaurantDataRepository.findByKakaoReviewProcessedFalse(
                    PageRequest.of(
                        0,
                        BATCH_SIZE,
                    ),
                )
            val futures = batch.map(::processRestaurantAsync)

            CompletableFuture
                .allOf(*futures.toTypedArray())
                .whenCompleteAsync({ _, ex ->
                    // 모든 작업이 끝났거나, 하나라도 에러가 나면 이 블록이 호출됩니다.
                    val failures = futures.count { it.isCompletedExceptionally }
                    log.info(
                        "✅ All review updates completed. successes={} failures={}",
                        batch.size - failures,
                        failures,
                    )
                }, callbackExecutor)
        }
    }

    private fun isQueueFull(): Boolean {
        val pool = executor.threadPoolExecutor // 실제 ThreadPoolExecutor
        val queue = pool.queue

        if (queue.remainingCapacity() < BATCH_SIZE) {
            return true
        }
        return false
    }

    private fun processRestaurantAsync(data: RawRestaurantData): CompletableFuture<Void> =
        CompletableFuture.runAsync({
            runCatching {
                Thread.sleep(LIMIT_REQUEST_TIME)
                kakaoReviewService.saveKakaoReview(data)
            }.onFailure { ex ->
                log.error(
                    "❌ Failed to process review for id={}, kakaoID = {}",
                    data.id,
                    data.kakaoPlaceData?.id,
                    ex,
                )
            }
        }, executor)
}
