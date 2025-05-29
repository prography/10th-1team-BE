package org.prography.kakao.place.external

import feignconfig.KakaoFeignConfig
import org.prography.kakao.place.dto.KakaoPlaceResponse
import org.prography.kakao.region.dto.Coord2AddressResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kakaoClient",
    url = "https://dapi.kakao.com",
    configuration = [KakaoFeignConfig::class],
)
interface KakaoFeignClient {
    @GetMapping("/v2/local/search/category.json")
    fun searchByCategory(
        @RequestParam("category_group_code") categoryGroupCode: String?,
        @RequestParam("page") page: Int,
        @RequestParam("rect") rect: String?,
    ): KakaoPlaceResponse

    @GetMapping("/v2/local/geo/coord2regioncode.json")
    fun coord2Address(
        @RequestParam("x") x: String, // 경도
        @RequestParam("y") y: String, // 위도
    ): Coord2AddressResponse
}
