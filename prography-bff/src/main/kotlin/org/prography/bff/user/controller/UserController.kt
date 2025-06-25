package org.prography.bff.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.controller.model.UserInfoResponseDto
import java.util.*
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse

interface UserController {
    @Operation(
        summary = "내 정보 조회 API",
        description = "JWT 인증된 사용자의 정보를 반환합니다.",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(schema = Schema(implementation = UserInfoResponseDto::class))],
            ),
            SwaggerApiResponse(
                responseCode = "401",
                description = "인증 실패",
            ),
            SwaggerApiResponse(
                responseCode = "403",
                description = "인가 실패",
            ),
        ],
    )
    fun getMyInfo(userId: UUID): ApiResponse<UserInfoResponseDto>
}
