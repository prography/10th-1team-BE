package org.prography.bff.user.domain.service

import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.user.controller.model.UserUpdateRequestDto
import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.prography.bff.user.domain.repository.UserRepository
import org.prography.bff.user.domain.service.model.UserInfoDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {
    private fun getActiveUser(userId: UUID): User {
        val user =
            userRepository.findByIdOrNull(userId)
                ?: throw NotFoundException.UserNotFoundException()
        user.validateActive()
        return user
    }

    /**
     * 신규 유저는 회원가입 및 신규 유저 플래그 추가
     */
    @Transactional
    fun registerIfNotExists(
        provider: Provider,
        providerId: String,
    ): UserInfoDto {
        val user =
            userRepository.findByProviderAndProviderId(provider, providerId)
                ?: return UserInfoDto.fromNewUser(
                    userRepository.save(
                        User(
                            provider = provider,
                            providerId = providerId,
                            nickname = NicknameGenerator.generate(),
                        ),
                    ),
                )

        // 회원 탈퇴 유저는 자동 복구
        if (user.status) user.reactivate()
        return UserInfoDto.fromUser(user)
    }

    fun getUserInfo(userId: UUID): UserInfoDto {
        val user = getActiveUser(userId)
        return UserInfoDto.fromUser(user)
    }

    @Transactional
    fun withdraw(userId: UUID) {
        val user = getActiveUser(userId)
        user.withdraw() // soft delete
        userRepository.save(user)
    }

    @Transactional
    fun updateUserInfo(
        userId: UUID,
        request: UserUpdateRequestDto,
    ): UserInfoDto {
        val user = getActiveUser(userId)

        request.nickname?.let {
            user.changeNickName(it)
        }

        val updateUser = userRepository.save(user)
        return UserInfoDto.fromUser(updateUser)
    }
}
