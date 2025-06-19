package org.prography.bff.auth.external.kakao

import org.prography.bff.auth.external.kakao.dto.KakaoTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "kakaoTokenClient", url = "https://kauth.kakao.com")
interface KakaoTokenClient {
    @PostMapping(
        "/oauth/token",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
    )
    fun getToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("redirect_uri") redirectUri: String,
        @RequestParam("code") code: String,
    ): KakaoTokenResponse
}
