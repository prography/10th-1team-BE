package org.prography.bff.auth.domain.port

import org.prography.bff.user.domain.entity.Provider

data class OAuthUserInfo(
    val provider: Provider, // e.g., "kakao", "naver"
    val providerId: String, // e.g., "123456"
    val nickname: String?,
)
