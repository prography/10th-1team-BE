package org.prography.bff.user.domain.service.model

import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import java.util.*

data class UserInfoDto(
    val userId: UUID,
    val provider: Provider,
    val nickname: String,
    val level: Int,
    val isNewUser: Boolean,
) {
    companion object {
        fun fromUser(
            user: User,
            isNewUser: Boolean = false,
        ): UserInfoDto {
            return UserInfoDto(
                userId = user.id,
                provider = user.provider,
                nickname = user.nickname,
                level = user.level,
                isNewUser = isNewUser,
            )
        }
    }
}
