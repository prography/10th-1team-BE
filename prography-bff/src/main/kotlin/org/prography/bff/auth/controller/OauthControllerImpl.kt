package org.prography.bff.auth.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.prography.bff.auth.controller.model.LoginResponseDto
import org.prography.bff.auth.domain.service.OAuthLoginService
import org.prography.bff.config.exception.auth.UnauthorizedException
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.domain.entity.Provider
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class OauthControllerImpl(
    private val oAuthLoginService: OAuthLoginService,
    @Value("\${cookie.access-token.max-age:86400}")
    private val accessTokenMaxAge: Int,
    @Value("\${cookie.refresh-token.max-age:2592000}")
    private val refreshTokenMaxAge: Int,
) : OauthController {
    companion object {
        private const val ACCESS_TOKEN_NAME = "accessToken"
        private const val REFRESH_TOKEN_NAME = "refreshToken"
        private const val COOKIE_PATH = "/"
        private const val COOKIE_SAMESITE_NONE = "None"
    }

    @PostMapping("/oauth2/code/{provider}")
    override fun oauthCallback(
        @PathVariable provider: Provider,
        @RequestParam code: String,
        response: HttpServletResponse,
    ): ApiResponse<LoginResponseDto> {
        val tokenDto = oAuthLoginService.login(provider, code)
        setAuthCookies(tokenDto.accessToken, tokenDto.refreshToken, response)
        return ApiResponse.success(LoginResponseDto(isNewUser = tokenDto.isNewUser))
    }

    private fun setAuthCookies(
        accessToken: String,
        refreshToken: String,
        response: HttpServletResponse,
    ) {
        // Access Token Cookie
        val accessTokenCookie =
            Cookie(ACCESS_TOKEN_NAME, accessToken).apply { // 직접 접근
                path = COOKIE_PATH
                isHttpOnly = true
                secure = true
                maxAge = accessTokenMaxAge
                setAttribute("SameSite", COOKIE_SAMESITE_NONE)
            }
        response.addCookie(accessTokenCookie)

        // Refresh Token Cookie
        val refreshTokenCookie =
            Cookie(REFRESH_TOKEN_NAME, refreshToken).apply { // 직접 접근
                path = COOKIE_PATH
                isHttpOnly = true
                secure = true
                maxAge = refreshTokenMaxAge
                setAttribute("SameSite", COOKIE_SAMESITE_NONE)
            }
        response.addCookie(refreshTokenCookie)
    }

    @PostMapping("/refresh-token")
    override fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ApiResponse<LoginResponseDto> {
        val refreshToken =
            request.cookies?.firstOrNull { it.name == REFRESH_TOKEN_NAME }?.value // 직접 접근
                ?: throw UnauthorizedException.InvalidRefreshTokenException()

        val tokenDto = oAuthLoginService.refreshAccessToken(refreshToken)

        // Todo: 리프레시 토큰 로테이션 적용
        setAuthCookies(tokenDto.accessToken, tokenDto.refreshToken, response)

        return ApiResponse.success(
            LoginResponseDto(
                isNewUser = tokenDto.isNewUser,
            ),
        )
    }
}
