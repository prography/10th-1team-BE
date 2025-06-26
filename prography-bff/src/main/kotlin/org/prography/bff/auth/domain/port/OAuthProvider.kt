package org.prography.bff.auth.domain.port

import org.prography.bff.user.domain.entity.Provider

interface OAuthProvider {
    fun supports(provider: Provider): Boolean

    fun requestAccessToken(code: String): String

    fun requestUserInfo(accessToken: String): OAuthUserInfo
}
