package org.prography.bff.user.domain.service.model

import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import java.time.LocalDateTime
import java.util.*

data class UserInfoDto(
    val userId: UUID,
    val provider: Provider,
    val nickname: String,
    val level: Int,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun fromUser(user: User): UserInfoDto {
            return UserInfoDto(
                user.id,
                user.provider,
                user.nickname,
                user.level,
                user.createdAt,
            )
        }
    }
}
