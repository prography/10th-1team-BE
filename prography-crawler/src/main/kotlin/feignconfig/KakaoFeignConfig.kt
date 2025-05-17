package feignconfig

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

class KakaoFeignConfig {
    @Value("\${kakao.api.key}")
    private val kakaoApiKey: String? = null

    @Bean
    fun kakaoRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            template.header(
                "Authorization",
                "KakaoAK $kakaoApiKey",
            )
        }
    }
}
