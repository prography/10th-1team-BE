package org.prography

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.prography.kakao.review.service.KakaoReviewService
import org.prography.naver.review.service.NaverReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExternalApiIntegrationTest {
    @Autowired
    private lateinit var naverReviewService: NaverReviewService

    @Test
    fun naver_review_api() {
        val naverInfo = naverReviewService.findNaverInfo("33963834")

        assertThat(naverInfo).isNotNull
        assertThat(naverInfo.size).isEqualTo(2)
        assertThat(naverInfo[1].data.visitorReviewStats?.id).isEqualTo("33963834")
    }

    @Autowired
    private lateinit var kakaoReviewService: KakaoReviewService

    @Test
    fun kakao_review_api() {
        val kakaoReviewResponse = kakaoReviewService.searchReviewsByKakaoId("461823389")

        assertThat(kakaoReviewResponse).isNotNull
        assertThat(kakaoReviewResponse.reviews).isNotNull
    }
}
