package org.prography.bff.user.controller

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
        @RequestParam("year") year: Int, // 검증 어노테이션 유효성 예외 HV000151: A method overriding another method must not redefine the parameter constraint configuration
        @RequestParam("month") month: Int,
    ): ApiResponse<UserActivityDto> {
        val yearMonth =
            YearMonth.of(
                year.coerceIn(2000, 2100),
                month.coerceIn(1, 12),
            )
        val from: LocalDateTime = yearMonth.atDay(1).atStartOfDay()
        val to: LocalDateTime = yearMonth.atEndOfMonth().atTime(LocalTime.MAX)

        val voteActivities: List<VoteActivity> = activityService.getVoteActivities(userId, from, to)
        return ApiResponse.success(
            UserActivityDto(
                votes =
                    voteActivities.map {
                        ActivityVote(
                            placeId = it.placeId,
                            placeName = it.placeName,
                            category = it.category,
                            platform = MatchPlatform.fromString(it.platform),
                            reasons = it.reasons,
                            votedDate = it.votedDate,
                        )
                    },
                bookmarks = emptyList(),
                groups = emptyList(),
            ),
        )
    }

    @GetMapping("/activity/group")
    override fun getGroupActivity(
        @AuthUser userId: UUID,
    ): ApiResponse<List<UserGroup>> {
        return ApiResponse.success(emptyList())
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
