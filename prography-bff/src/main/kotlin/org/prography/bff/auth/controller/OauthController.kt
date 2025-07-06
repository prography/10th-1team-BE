package org.prography.bff.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.prography.bff.auth.controller.model.LoginResponseDto
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.domain.entity.Provider

@Tag(
    name = "Auth",
    description = "OAuth Token 전용 API",
)
interface OauthController {
    @Operation(summary = "Oauth 로그인 콜백", description = "카카오/네이버의 인가 코드를 받아 로그인합니다.")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LoginResponseDto::class),
                    ),
                ],
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "잘못된 토큰 요청",
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "잘못된 제공자",
            ),
        ],
    )
    fun oauthCallback(
        provider: Provider,
        code: String,
        response: HttpServletResponse,
    ): ApiResponse<LoginResponseDto>

    @Operation(summary = "토큰 재발급", description = "RefreshToken 기반으로 유효한 토큰을 재생성합니다.")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "토큰 재발급 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LoginResponseDto::class),
                    ),
                ],
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "401",
                description = "유효하지 않은 리프레시 토큰",
            ),
        ],
    )
    fun refreshToken(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ApiResponse<LoginResponseDto>
}
