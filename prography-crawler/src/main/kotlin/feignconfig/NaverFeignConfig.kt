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
            template.header("Accept", "application/json, text/plain, */*")
            template.header("Accept-Language", "ko-KR,ko;q=0.9")
            template.header("Origin", "https://map.naver.com")
            template.header("Connection", "keep-alive")
            template.header("Sec-Fetch-Site", "same-origin")
            template.header("Sec-Fetch-Mode", "cors")
            template.header("Sec-Fetch-Dest", "empty")
        }
}
