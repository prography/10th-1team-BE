package org.prography.batch.service

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KakaoReviewBatchServiceTest {
    @Autowired
    lateinit var kakaoReviewBatchService: KakaoReviewBatchService
}
