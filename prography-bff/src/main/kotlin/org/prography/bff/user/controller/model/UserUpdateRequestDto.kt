package org.prography.bff.user.controller.model

data class UserUpdateRequestDto(
    val nickname: String? = null,
    // 변경 가능 필드를 추가
)
