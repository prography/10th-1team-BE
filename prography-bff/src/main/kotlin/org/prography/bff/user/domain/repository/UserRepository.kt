package org.prography.bff.user.domain.repository

import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByProviderAndProviderId(
        provider: Provider,
        providerId: String,
    ): User?
}
