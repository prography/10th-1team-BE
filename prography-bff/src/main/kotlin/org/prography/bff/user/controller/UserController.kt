package org.prography.bff.user.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.controller.model.UserInfoResponseDto
import org.prography.bff.user.controller.model.UserUpdateRequestDto
import java.util.*
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerApiResponse

@Tag(
    name = "User",
    description = "유저 정보 전용 API",
)
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

    @Operation(
        summary = "회원 탈퇴 API",
        description = "JWT 인증된 사용자를 탈퇴 처리합니다. (Soft Delete)",
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "탈퇴 성공",
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
    fun withdraw(userId: UUID): ApiResponse<String>

    @Operation(
        summary = "내 정보 변경 API",
        description = "JWT 인증된 사용자의 정보를 부분 변경합니다.",
        requestBody =
            RequestBody(
                required = true,
                content = [Content(schema = Schema(implementation = UserUpdateRequestDto::class))],
            ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "변경 성공",
                content = [Content(schema = Schema(implementation = UserInfoResponseDto::class))],
            ),
            SwaggerApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
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
    fun updateMyInfo(
        userId: UUID,
        request: UserUpdateRequestDto,
    ): ApiResponse<UserInfoResponseDto>
}
