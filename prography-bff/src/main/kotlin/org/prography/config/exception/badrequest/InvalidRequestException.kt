package org.prography.config.exception.badrequest

sealed class InvalidRequestException(
    override val message: String,
) : RuntimeException(message) {
    class CursorInvalidRequest :
        InvalidRequestException("유효하지 않은 ID 입니다")
}
