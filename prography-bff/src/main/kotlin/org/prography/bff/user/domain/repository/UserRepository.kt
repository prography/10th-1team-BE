package org.prography.bff.user.domain.repository

import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByProviderAndProviderId(
        provider: Provider,
        providerId: String,
    ): User?
}
