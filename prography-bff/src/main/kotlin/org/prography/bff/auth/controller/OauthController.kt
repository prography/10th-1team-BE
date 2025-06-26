package org.prography.bff.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.prography.bff.auth.controller.model.TokenResponseDto
import org.prography.bff.config.response.ApiResponse

interface OauthController {
    @Operation(summary = "카카오 로그인 콜백", description = "카카오 인가 코드를 받아 로그인합니다.")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TokenResponseDto::class),
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
    fun kakaoCallback(code: String): ApiResponse<TokenResponseDto>

    @Operation(summary = "네이버 로그인 콜백", description = "네이버 인가 코드를 받아 로그인합니다.")
    @ApiResponses(
        value = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = TokenResponseDto::class),
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
    fun naverCallback(code: String): ApiResponse<TokenResponseDto>
}
