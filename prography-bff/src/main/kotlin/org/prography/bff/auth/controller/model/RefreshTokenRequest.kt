package org.prography.bff.auth.controller.model

import io.swagger.v3.oas.annotations.media.Schema

data class RefreshTokenRequest(
    @Schema(
        example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkNTY4NzQwMS0wMzllLTQxNDItOGEwMy01YTE4MjJhMWQ1NjQiLCJpYXQiOjE3NTExMDE5ODksImV4cCI6MTc1MzY5Mzk4OX0.mF99k8mZexU9Lwc4vdRMingqVCyAXXjMpDugF6-kH1g",
    ) val refreshToken: String,
)
