package org.prography.bff.auth.domain.service

import org.prography.bff.auth.domain.model.LoginDto
import org.prography.bff.auth.domain.model.TokenType
import org.prography.bff.auth.domain.port.OAuthProvider
import org.prography.bff.config.exception.notfound.NotFoundException
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
}
