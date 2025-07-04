package org.prography.bff.user.controller

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.user.controller.model.ActivityVote
import org.prography.bff.user.controller.model.UserActivityDto
import org.prography.bff.user.controller.model.UserGroup
import org.prography.bff.user.controller.model.UserInfoResponseDto
import org.prography.bff.user.controller.model.UserUpdateRequestDto
import java.util.UUID
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

    @Operation(
        summary = "유저의 활동 데이터 조회",
        description = "유저의 활동 데이터를 캘린더 기준으로 조회하는 API 입니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1206-18491&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(schema = Schema(implementation = UserActivityDto::class))],
            ),
        ],
    )
    fun getActivityCalendar(
        userId: UUID,
        year: Int,
        month: Int,
    ): ApiResponse<List<UserActivityDto>>

    @Operation(
        summary = "유저가 생성한 그룹 조회",
        description = "유저의 활동 데이터인 그룹을 조회하는 API 입니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1206-18491&t=sImFG24XTWo1974L-11",
            ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(schema = Schema(implementation = UserGroup::class))],
            ),
        ],
    )
    fun getGroupActivity(userId: UUID): ApiResponse<List<UserGroup>>

    @Operation(
        summary = "유저의 투표 데이터 조회",
        description = "유저의 활동 데이터인 투표 내역을 조회하는 API 입니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1218-21666&t=sImFG24XTWo1974L-11",
            ),
        responses = [
            SwaggerApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(schema = Schema(implementation = ActivityVote::class))],
            ),
        ],
    )
    fun getVoteActivity(userId: UUID): ApiResponse<List<ActivityVote>>
}
