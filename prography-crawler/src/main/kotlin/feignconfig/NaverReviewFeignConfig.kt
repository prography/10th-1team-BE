package feignconfig

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class NaverReviewFeignConfig {
    companion object {
        private const val UA =
            "Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) " +
                "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1"
        private const val REFERER = "https://m.place.naver.com"
    }

    @Bean
    fun naverReviewHeaderInterceptor(): RequestInterceptor =
        RequestInterceptor { template ->
            template.header("User-Agent", UA)
            template.header("Referer", REFERER)
        }
}
