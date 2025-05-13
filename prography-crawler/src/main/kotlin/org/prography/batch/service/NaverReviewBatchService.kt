package org.prography.batch.service

import org.prography.batch.domain.BatchConstants.BATCH_DELAY
import org.prography.batch.domain.BatchConstants.BATCH_SIZE
import org.prography.naver.place.service.NaverPlaceService
import org.prography.naver.review.service.NaverReviewService
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
        log.info("unprocessed count: {}", unprocessedCount)
        if (unprocessedCount >= BATCH_SIZE) {
            // 최초 1,000건만 조회
            val batch =
                rawRestaurantDataRepository.findByNaverReviewProcessedFalse(
                    PageRequest.of(
                        0,
                        BATCH_SIZE,
                    ),
                )

            val futures =
                batch.map { restaurant ->
                    CompletableFuture.runAsync(
                        {
                            try {
                                naverPlaceService.saveNaverPlaceData(restaurant)
                                naverReviewService.saveNaverReview(restaurant)
                            } catch (e: Exception) {
                                log.error("Failed to process review for id: {}", restaurant.id, e)
                            } finally {
                                Thread.sleep(10_000)
                            }
                        },
                        executor,
                    )
                }

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
                    batch.forEach { it.naverReviewProcessed = true }
                    rawRestaurantDataRepository.saveAll(batch)
                }, callbackExecutor)
        }
    }

    fun scrapAllRestaurants() {
        val all = rawRestaurantDataRepository.findAll()
        all.forEach {
            naverPlaceService.saveNaverPlaceData(it)
            naverReviewService.saveNaverReview(it)
        }
        all.forEach { it.naverReviewProcessed = true }
        rawRestaurantDataRepository.saveAll(all)
    }

    private fun isQueueFull(): Boolean {
        val pool = executor.threadPoolExecutor // 실제 ThreadPoolExecutor
        val queue = pool.queue

        if (queue.remainingCapacity() < BATCH_SIZE) {
            return true
        }
        return false
    }
}
