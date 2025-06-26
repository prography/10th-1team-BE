package org.prography.bff.user.domain.service

import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.prography.bff.user.domain.repository.UserRepository
import org.prography.bff.user.domain.service.model.UserInfoDto
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
) {
    fun registerIfNotExists(
        provider: Provider,
        providerId: String,
    ): User {
        return userRepository.findByProviderAndProviderId(provider, providerId)
            ?: userRepository.save(
                User(
                    provider = provider,
                    providerId = providerId,
                    nickname = NicknameGenerator.generate(),
                ),
            )
    }

    fun getUserInfo(userId: UUID): UserInfoDto {
        val user =
            userRepository.findByIdOrNull(userId) ?: throw NotFoundException.UserNotFoundException()

        return UserInfoDto.fromUser(user)
    }
}
