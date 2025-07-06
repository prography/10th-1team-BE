package org.prography.bff.auth.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponseDto(
    @field:Schema(description = "신규 가입 여부", example = "false", type = "boolean")
    val isNewUser: Boolean,
)
