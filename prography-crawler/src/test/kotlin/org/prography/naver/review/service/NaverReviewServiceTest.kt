package org.prography.naver.review.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NaverReviewServiceTest {
    @Autowired
    private lateinit var naverReviewService: NaverReviewService

    @Test
    fun naver_review_example() {
        val naverInfo = naverReviewService.findNaverInfo("33963834")

        assertThat(naverInfo).isNotNull
        assertThat(naverInfo.size).isEqualTo(2)
        assertThat(naverInfo[1].data.visitorReviewStats?.id).isEqualTo("33963834")
        // getVisitorReviews, getVisitorReviewStats 2가지
    }
}
