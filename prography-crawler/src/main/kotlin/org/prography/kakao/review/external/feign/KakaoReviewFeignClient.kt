package org.prography.kakao.review.external.feign

import feignconfig.KakaoReviewFeignConfig
import org.prography.kakao.review.external.dto.KakaoReviewResponse
import org.prography.kakao.review.external.dto.Order
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakaoReviewClient",
    url = "https://place-api.map.kakao.com",
    configuration = [KakaoReviewFeignConfig::class],
)
interface KakaoReviewFeignClient {
    @GetMapping("/places/tab/reviews/kakaomap/{kakaoId}")
    fun searchReviewsByKakaoId(
        @RequestParam("previous_last_review_id") previousLastReviewId: Long,
        @RequestParam("order") order: Order,
        @RequestParam("only_photo_review") onlyPhotoReview: Boolean,
        @PathVariable("kakaoId") kakaoId: String,
    ): KakaoReviewResponse
}
