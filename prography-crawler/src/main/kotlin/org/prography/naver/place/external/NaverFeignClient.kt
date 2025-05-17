package org.prography.naver.place.external

import feignconfig.NaverFeignConfig
import org.prography.naver.place.external.dto.NaverPlaceData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "naverClient",
    url = "https://map.naver.com",
    configuration = [NaverFeignConfig::class],
)
interface NaverFeignClient {
    /**
     * 네이버 통합 장소 검색
     *
     * @param query        검색어 (자동 URL-encoding)
     * @param type         보통 `"all"`
     * @param searchCoord  `"lng;lat"` 형식 (예: `"127.1056;37.3595"`)
     */
    @GetMapping("/p/api/search/allSearch")
    fun searchNaverInfo(
        @RequestParam("query") query: String,
        @RequestParam("type") type: String,
        @RequestParam("searchCoord") searchCoord: String,
    ): NaverPlaceData
}
