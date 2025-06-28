package org.prography.bff.auth.domain.service

import org.prography.bff.auth.domain.model.LoginDto
import org.prography.bff.auth.domain.model.TokenType
import org.prography.bff.auth.domain.port.OAuthProvider
import org.prography.bff.config.exception.auth.UnauthorizedException
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.config.security.JwtProvider
import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.service.UserService
import org.springframework.stereotype.Service

@Service
class OAuthLoginService(
    private val providers: List<OAuthProvider>,
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
) {
    fun login(
        providerName: Provider,
        code: String,
    ): LoginDto {
        val provider =
            providers.find { it.supports(providerName) }
                ?: throw NotFoundException.ProviderNotFoundException()

        val token = provider.requestAccessToken(code)
        val userInfo = provider.requestUserInfo(token)

        val user = userService.registerIfNotExists(userInfo.provider, userInfo.providerId)
        return LoginDto(
            accessToken =
                jwtProvider.createToken(
                    tokenType = TokenType.ACCESS_TOKEN,
                    subject = user.id.toString(),
                ),
            refreshToken =
                jwtProvider.createToken(
                    tokenType = TokenType.REFRESH_TOKEN,
                    subject = user.id.toString(),
                ),
        )
    }

    fun refreshAccessToken(refreshToken: String): LoginDto {
        // 1. 토큰 유효성 검증
        if (!jwtProvider.validateToken(refreshToken)) {
            throw UnauthorizedException.InvalidRefreshTokenException()
        }

        // 2. 토큰에서 사용자 ID 추출
        val userId = jwtProvider.getUserId(refreshToken)

        // 3. 사용자 존재 여부 확인 (필요시)
        val user = userService.getUserInfo(userId)

        // 4. 새로운 액세스 토큰 및 리프레시 토큰 생성
        return LoginDto(
            accessToken =
                jwtProvider.createToken(
                    tokenType = TokenType.ACCESS_TOKEN,
                    subject = user.userId.toString(),
                ),
            refreshToken =
                jwtProvider.createToken(
                    tokenType = TokenType.REFRESH_TOKEN,
                    subject = user.userId.toString(),
                ),
        )
    }
}
