package feignconfig

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class NaverFeignConfig {
    companion object {
        private const val UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36"
    }

    /** 네이버 지도 API 요청용 기본 헤더 */
    @Bean
    fun naverHeaderInterceptor(): RequestInterceptor =
        RequestInterceptor { template ->
            template.header("User-Agent", UA)
            template.header("Referer", "https://map.naver.com")
        }
}
