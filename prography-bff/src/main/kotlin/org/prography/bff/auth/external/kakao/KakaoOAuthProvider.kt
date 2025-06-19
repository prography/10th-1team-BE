package org.prography.bff.auth.external.kakao

import feign.FeignException
import org.prography.bff.auth.domain.port.OAuthProvider
import org.prography.bff.auth.domain.port.OAuthUserInfo
import org.prography.bff.config.exception.external.ExternalApiException
import org.prography.bff.user.domain.entity.Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class KakaoOAuthProvider(
    private val kakaoTokenClient: KakaoTokenClient,
    private val kakaoUserInfoClient: KakaoUserInfoClient,
    @Value("\${kakao.client-id}") private val clientId: String,
    @Value("\${kakao.redirect-uri}") private val redirectUri: String,
    @Value("\${kakao.client-secret}") private val clientSecret: String,
) : OAuthProvider {
    override fun supports(provider: Provider) = provider == Provider.KAKAO

    override fun requestAccessToken(code: String): String {
        try {
            val token =
                kakaoTokenClient.getToken(
                    grantType = "authorization_code",
                    clientId = clientId,
                    redirectUri = redirectUri,
                    clientSecret = clientSecret,
                    code = code,
                )
            return token.accessToken
        } catch (e: FeignException) {
            val reason = e.contentUTF8()
            val status =
                when (e.status()) {
                    400 -> HttpStatus.BAD_REQUEST
                    else -> HttpStatus.BAD_GATEWAY
                }
            throw ExternalApiException.KakaoTokenRequestException("토큰 요청 실패: $reason", status)
        }
    }

    override fun requestUserInfo(accessToken: String): OAuthUserInfo {
        val info = kakaoUserInfoClient.getUserInfo("Bearer $accessToken")
        return OAuthUserInfo(
            provider = Provider.KAKAO,
            providerId = info.id,
            nickname = info.kakaoAccount?.profile?.nickname,
        )
    }
}
