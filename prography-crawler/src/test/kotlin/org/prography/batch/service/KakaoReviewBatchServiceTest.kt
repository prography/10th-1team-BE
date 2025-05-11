package org.prography.batch.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KakaoReviewBatchServiceTest {
    @Autowired
    lateinit var kakaoReviewBatchService: KakaoReviewBatchService

    @Test
    fun searchAllReviewsByKakaoId() {
        kakaoReviewBatchService.scrapAllRestaurants()
    }
}
