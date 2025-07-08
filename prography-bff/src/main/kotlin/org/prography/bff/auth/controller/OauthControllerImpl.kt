package org.prography.bff.auth.controller

import org.prography.bff.auth.controller.model.RefreshTokenRequest
import org.prography.bff.auth.controller.model.TokenResponseDto
import org.prography.bff.auth.domain.service.OAuthLoginService
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.domain.entity.Provider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class OauthControllerImpl(
    private val oAuthLoginService: OAuthLoginService,
) : OauthController {
    @PostMapping("/oauth2/code/{provider}")
    override fun oauthCallback(
        @PathVariable provider: Provider,
        @RequestParam code: String,
    ): ApiResponse<TokenResponseDto> {
        val tokenDto = oAuthLoginService.login(provider, code)
        return ApiResponse.success(
            TokenResponseDto(
                accessToken = tokenDto.accessToken,
                refreshToken = tokenDto.refreshToken,
                isNewUser = tokenDto.isNewUser,
            ),
        )
    }

    @PostMapping("/refresh-token")
    override fun refreshToken(
        @RequestBody request: RefreshTokenRequest,
    ): ApiResponse<TokenResponseDto> {
        val tokenDto = oAuthLoginService.refreshAccessToken(request.refreshToken)

        return ApiResponse.success(
            TokenResponseDto(
                accessToken = tokenDto.accessToken,
                refreshToken = tokenDto.refreshToken,
                isNewUser = tokenDto.isNewUser,
            ),
        )
    }
}
