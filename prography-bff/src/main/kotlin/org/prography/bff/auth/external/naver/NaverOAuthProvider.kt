package org.prography.bff.auth.external.naver

import feign.FeignException
import org.prography.bff.auth.domain.port.OAuthProvider
import org.prography.bff.auth.domain.port.OAuthUserInfo
import org.prography.bff.config.exception.external.ExternalApiException
import org.prography.bff.user.domain.entity.Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class NaverOAuthProvider(
    private val naverTokenClient: NaverTokenFeign,
    private val naverUserClient: NaverUserFeign,
    @Value("\${naver.client-id}") private val clientId: String,
    @Value("\${naver.client-secret}") private val clientSecret: String,
) : OAuthProvider {
    override fun supports(provider: Provider) = provider == Provider.NAVER

    override fun requestAccessToken(code: String): String {
        try {
            val token =
                naverTokenClient.getToken(
                    grantType = "authorization_code",
                    clientId = clientId,
                    clientSecret = clientSecret,
                    code = code,
                    state = "random",
                )
            return token.accessToken
        } catch (e: FeignException) {
            val reason = e.contentUTF8()
            val status =
                when (e.status()) {
                    400 -> HttpStatus.BAD_REQUEST
                    else -> HttpStatus.BAD_GATEWAY
                }
            throw ExternalApiException.NaverTokenRequestException("네이버 토큰 요청 실패: $reason", status)
        }
    }

    override fun requestUserInfo(accessToken: String): OAuthUserInfo {
        val res = naverUserClient.getUserInfo("Bearer $accessToken")
        val info = res.response
        return OAuthUserInfo(
            provider = Provider.NAVER,
            providerId = info.id,
            nickname = info.nickname,
        )
    }
}
