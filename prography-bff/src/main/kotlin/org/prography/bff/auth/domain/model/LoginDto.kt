package org.prography.bff.auth.domain.model

data class LoginDto(
    val accessToken: String,
    val refreshToken: String,
)
