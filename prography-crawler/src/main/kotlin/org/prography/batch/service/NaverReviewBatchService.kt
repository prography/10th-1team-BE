package org.prography.batch.service

import org.prography.batch.domain.BatchConstants.BATCH_DELAY
import org.prography.batch.domain.BatchConstants.BATCH_SIZE
import org.prography.batch.domain.BatchConstants.LIMIT_REQUEST_TIME
import org.prography.naver.place.service.NaverPlaceService
import org.prography.naver.review.service.NaverReviewService
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.*
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class NaverReviewBatchService(
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
    private val naverPlaceService: NaverPlaceService,
    private val naverReviewService: NaverReviewService,
    @Qualifier("naverScrapExecutor")
    private val executor: ThreadPoolTaskExecutor,
    @Qualifier("callbackExecutor")
    private val callbackExecutor: ThreadPoolTaskExecutor, // 추가
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    // 애플리케이션 시작 후 30초 대기 → 매 30초마다 실행
    @Scheduled(initialDelay = BATCH_DELAY, fixedDelay = BATCH_DELAY)
    fun scrapNaverReview() {
        if (isQueueFull()) {
            return
        }

        val unprocessedCount = rawRestaurantDataRepository.countByNaverReviewProcessedFalse()
        log.info("unprocessed naver count: {}", unprocessedCount)
        if (unprocessedCount >= BATCH_SIZE) {
            // 최초 1,000건만 조회
            val batch =
                rawRestaurantDataRepository.findByNaverReviewProcessedFalse(
                    PageRequest.of(
                        0,
                        BATCH_SIZE,
                    ),
                )

            val futures = batch.map(::processRestaurantAsync)

            // 모든 작업 완료까지 대기

            CompletableFuture
                .allOf(*futures.toTypedArray())
                .whenCompleteAsync({ _, ex ->
                    // 모든 작업이 끝났거나, 하나라도 에러가 나면 이 블록이 호출됩니다.
                    val failures = futures.count { it.isCompletedExceptionally }
                    log.info(
                        "✅ All Naver updates completed. successes={} failures={}",
                        batch.size - failures,
                        failures,
                    )
                }, callbackExecutor)
        }
    }

    private fun processRestaurantAsync(data: RawRestaurantData): CompletableFuture<Void> =
        CompletableFuture.runAsync({
            runCatching {
                Thread.sleep(LIMIT_REQUEST_TIME)
                naverPlaceService.saveNaverPlaceData(data)
                naverReviewService.saveNaverReview(data)
            }.onFailure { ex ->
                log.error(
                    "❌ Failed to process review for id={}, kakaoID = {}",
                    data.id,
                    data.kakaoPlaceData?.id,
                    ex,
                )
                throw ex
            }
        }, executor)

    private fun isQueueFull(): Boolean {
        val pool = executor.threadPoolExecutor // 실제 ThreadPoolExecutor
        val queue = pool.queue

        if (queue.remainingCapacity() < BATCH_SIZE) {
            return true
        }
        return false
    }
}
