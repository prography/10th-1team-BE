package org.prography.bff.user.domain.service

import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.prography.bff.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
                    nickname = "익명",
                ),
            )
    }
}
