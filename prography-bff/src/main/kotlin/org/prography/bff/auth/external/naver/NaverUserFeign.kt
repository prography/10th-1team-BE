package org.prography.bff.auth.external.naver

import org.prography.bff.auth.external.naver.dto.NaverUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "naverUserClient", url = "https://openapi.naver.com")
interface NaverUserFeign {
    @GetMapping("/v1/nid/me")
    fun getUserInfo(
        @RequestHeader("Authorization") accessToken: String,
    ): NaverUserResponse
}
