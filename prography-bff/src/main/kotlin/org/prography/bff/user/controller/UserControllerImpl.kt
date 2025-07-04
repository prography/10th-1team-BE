package org.prography.bff.user.controller

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.prography.bff.user.controller.model.ActivityVote
import org.prography.bff.user.controller.model.UserActivityDto
import org.prography.bff.user.controller.model.UserGroup
import org.prography.bff.user.controller.model.UserInfoResponseDto
import org.prography.bff.user.controller.model.UserUpdateRequestDto
import org.prography.bff.user.domain.service.UserService
import org.prography.bff.user.service.UserActivityService
import org.prography.bff.user.service.model.VoteActivity
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.util.UUID

@RestController
@RequestMapping("/users")
class UserControllerImpl(
    private val userService: UserService,
    private val activityService: UserActivityService,
) : UserController {
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

    @GetMapping("/activity/calendar")
    override fun getActivityCalendar(
        @AuthUser userId: UUID,
        @Min(
            value = 2000,
            message = "year 최소 {value}이어야 합니다.",
        )
        @Max(
            value = 2100,
            message = "year 최대 {value}이어야 합니다.",
        )
        @RequestParam("year") year: Int,
        @Min(
            value = 1,
            message = "month 최소 {value}이어야 합니다.",
        )
        @Max(
            value = 12,
            message = "month 최대 {value}이어야 합니다.",
        )
        @RequestParam("month") month: Int,
    ): ApiResponse<List<UserActivityDto>> {
        val yearMonth =
            YearMonth.of(
                year.coerceIn(2000, 2100),
                month.coerceIn(1, 12),
            )
        val from: LocalDateTime = yearMonth.atDay(1).atStartOfDay()
        val to: LocalDateTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX)

        val voteActivities: List<VoteActivity> = activityService.getVoteActivities(userId, from, to)
        TODO("Not yet implemented")
    }

    @GetMapping("/activity/group")
    override fun getGroupActivity(
        @AuthUser userId: UUID,
    ): ApiResponse<List<UserGroup>> {
        TODO("Not yet implemented")
    }

    @GetMapping("/activity/vote")
    override fun getVoteActivity(
        @AuthUser userId: UUID,
    ): ApiResponse<List<ActivityVote>> {
        val activities =
            activityService.getVoteActivities(userId).map {
                ActivityVote(
                    placeId = it.placeId,
                    placeName = it.placeName,
                    category = it.category,
                    platform = MatchPlatform.fromString(it.platform),
                    reasons = it.reasons,
                    votedDate = it.votedDate,
                )
            }

        return ApiResponse.success(activities)
    }
}
