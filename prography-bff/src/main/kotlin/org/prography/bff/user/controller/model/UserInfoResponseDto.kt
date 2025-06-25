package org.prography.bff.user.controller.model

import org.prography.bff.user.domain.entity.Provider
import java.time.LocalDateTime
import java.util.*

data class UserInfoResponseDto(
    val userId: UUID,
    val provider: Provider,
    val nickname: String,
    val level: Int,
    val createdAt: LocalDateTime,
)
