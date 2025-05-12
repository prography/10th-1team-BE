package feignconfig

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class KakaoReviewFeignConfig {
    @Bean
    fun kakaoHeaderInterceptor(): RequestInterceptor =
        RequestInterceptor { requestTemplate ->
            requestTemplate.header(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Mobile Safari/537.36a",
            )
            requestTemplate.header("Referer", "https://place.map.kakao.com/")
            requestTemplate.header("pf", "web")
        }
}
