package org.prography.bff.auth.external.kakao

import org.prography.bff.auth.external.kakao.dto.KakaoUserInfo
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
interface KakaoUserInfoClient {
    @GetMapping("/v2/user/me")
    fun getUserInfo(
        @RequestHeader("Authorization") bearerToken: String,
    ): KakaoUserInfo
}
