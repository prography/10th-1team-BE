package org.prography.bff.auth.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponseDto(
    @field:Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val accessToken: String,
    @field:Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val refreshToken: String,
    @field:Schema(description = "신규 가입 여부", example = "false", type = "boolean")
    val isNewUser: Boolean,
)
