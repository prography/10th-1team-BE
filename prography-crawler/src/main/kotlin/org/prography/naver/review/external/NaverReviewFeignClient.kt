package org.prography.naver.review.external

import feignconfig.NaverReviewFeignConfig
import org.prography.naver.review.external.dto.req.GraphQLRequest
import org.prography.naver.review.external.dto.res.NaverReviewResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "naverReviewClient",
    url = "https://pcmap-api.place.naver.com",
    configuration = [NaverReviewFeignConfig::class], // Kotlin: use array with ::class
)
interface NaverReviewFeignClient {
    @PostMapping("/graphql") // value = ["/graphql"] 도 동일
    fun getVisitorReviews(
        @RequestParam reviewSort: String,
        @RequestBody body: List<GraphQLRequest<*>>,
    ): List<NaverReviewResponse>
}
