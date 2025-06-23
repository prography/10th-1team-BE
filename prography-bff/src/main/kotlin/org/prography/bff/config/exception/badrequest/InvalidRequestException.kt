package org.prography.bff.config.exception.badrequest

sealed class InvalidRequestException(
    override val message: String,
) : RuntimeException(message) {
    class InvalidTokenException : InvalidRequestException("유저 ID를 찾지 못했습니다.")
}
