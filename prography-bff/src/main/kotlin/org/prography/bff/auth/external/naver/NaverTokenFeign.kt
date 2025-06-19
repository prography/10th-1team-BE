package org.prography.bff.auth.external.naver

import org.prography.bff.auth.external.naver.dto.NaverTokenResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "naverTokenClient", url = "https://nid.naver.com")
interface NaverTokenFeign {
    @PostMapping("/oauth2.0/token")
    fun getToken(
        @RequestParam("grant_type") grantType: String,
        @RequestParam("client_id") clientId: String,
        @RequestParam("client_secret") clientSecret: String,
        @RequestParam("code") code: String,
        @RequestParam("state") state: String,
    ): NaverTokenResponse
}
