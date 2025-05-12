package org.prography.batch.service

import org.prography.batch.domain.BatchConstants.BATCH_DELAY
import org.prography.batch.domain.BatchConstants.BATCH_SIZE
import org.prography.kakao.review.service.KakaoReviewService
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.domain.PageRequest
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.stereotype.Service

@Service
class KakaoReviewBatchService(
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
    private val kakaoReviewService: KakaoReviewService,
    @Qualifier("kakaoScrapExecutor")
    private val executor: ThreadPoolTaskExecutor,
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
        if (unprocessedCount >= BATCH_SIZE) {
            // 최초 1,000건만 조회
            val batch =
                rawRestaurantDataRepository.findByKakaoReviewProcessedFalse(
                    PageRequest.of(
                        0,
                        BATCH_SIZE,
                    ),
                )
            kakaoReviewService.updateAllReviewsAsync(batch)
            log.info("Processed $unprocessedCount/${BATCH_SIZE}")
            batch.forEach { it.kakaoReviewProcessed = true }
            rawRestaurantDataRepository.saveAll(batch)
        }
    }

    fun scrapAllRestaurants() {
        val all = rawRestaurantDataRepository.findAll()
        kakaoReviewService.updateAllReviewsAsync(all)
        all.forEach { it.kakaoReviewProcessed = true }
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
