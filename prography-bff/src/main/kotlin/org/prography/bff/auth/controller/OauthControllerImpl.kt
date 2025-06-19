package org.prography.bff.auth.controller

import org.prography.bff.auth.controller.model.TokenResponseDto
import org.prography.bff.auth.domain.service.OAuthLoginService
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.domain.entity.Provider
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login/oauth2")
class OauthControllerImpl(
    private val oAuthLoginService: OAuthLoginService,
) : OauthController {
    @PostMapping("/code/kakao")
    override fun kakaoCallback(
        @RequestParam code: String,
    ): ApiResponse<TokenResponseDto> {
        val tokenDto = oAuthLoginService.login(Provider.KAKAO, code)
        return ApiResponse.success(
            TokenResponseDto(
                accessToken = tokenDto.accessToken,
                refreshToken = tokenDto.refreshToken,
            ),
        )
    }

    @PostMapping("/code/naver")
    override fun naverCallback(
        @RequestParam code: String,
    ): ApiResponse<TokenResponseDto> {
        val tokenDto = oAuthLoginService.login(Provider.NAVER, code)
        return ApiResponse.success(
            TokenResponseDto(
                accessToken = tokenDto.accessToken,
                refreshToken = tokenDto.refreshToken,
            ),
        )
    }
}
