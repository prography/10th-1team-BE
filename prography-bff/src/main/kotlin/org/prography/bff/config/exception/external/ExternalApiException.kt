package org.prography.bff.config.exception.external

import org.springframework.http.HttpStatus

sealed class ExternalApiException(
    override val message: String,
    val status: HttpStatus,
) : RuntimeException(message) {
    class KakaoTokenRequestException(
        message: String,
        status: HttpStatus = HttpStatus.BAD_GATEWAY,
    ) : ExternalApiException(message, status)

    class NaverTokenRequestException(
        message: String,
        status: HttpStatus = HttpStatus.BAD_GATEWAY,
    ) : ExternalApiException(message, status)
}
