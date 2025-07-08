package org.prography.bff.config.exception.auth

sealed class UnauthorizedException(
    override val message: String,
) : RuntimeException(message) {
    /**
     * 리프레시 토큰이 유효하지 않을 경우 발생하는 예외
     */
    class InvalidRefreshTokenException : UnauthorizedException("유효하지 않은 리프레시 토큰입니다.")

    /**
     * 엔티티와 유저간 소유가 일치하지 않을 경우 발생하는 예외
     */
    class NotOwnerException : UnauthorizedException("권한이 존재하지 않습니다.")
}
