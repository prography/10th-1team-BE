package org.prography.bff.user.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.prography.bff.user.controller.model.UserInfoResponseDto
import org.prography.bff.user.domain.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}
