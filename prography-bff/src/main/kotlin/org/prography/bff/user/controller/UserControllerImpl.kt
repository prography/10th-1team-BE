package org.prography.bff.user.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.prography.bff.user.controller.model.UserInfoResponseDto
import org.prography.bff.user.controller.model.UserUpdateRequestDto
import org.prography.bff.user.domain.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UserControllerImpl(private val userService: UserService) : UserController {
    @GetMapping("/me")
    override fun getMyInfo(
        @AuthUser userId: UUID,
    ): ApiResponse<UserInfoResponseDto> {
        val userInfo = userService.getUserInfo(userId)
        val response =
            UserInfoResponseDto(
                userInfo.userId,
                userInfo.provider,
                userInfo.nickname,
                userInfo.level,
                userInfo.createdAt,
            )
        return ApiResponse.success(response)
    }

    @DeleteMapping("/me")
    override fun withdraw(
        @AuthUser userId: UUID,
    ): ApiResponse<String> {
        userService.withdraw(userId)
        return ApiResponse.success("성공적으로 삭제되었습니다.")
    }

    @PatchMapping("/me")
    override fun updateMyInfo(
        @AuthUser userId: UUID,
        @RequestBody request: UserUpdateRequestDto,
    ): ApiResponse<UserInfoResponseDto> {
        val updatedUserInfo = userService.updateUserInfo(userId, request)
        val response =
            UserInfoResponseDto(
                updatedUserInfo.userId,
                updatedUserInfo.provider,
                updatedUserInfo.nickname,
                updatedUserInfo.level,
                updatedUserInfo.createdAt,
            )
        return ApiResponse.success(response)
    }
}
