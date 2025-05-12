package org.prography.config.exception.notfound

sealed class NotFoundException(
    override val message: String,
) : RuntimeException(message) {
    class PlaceNotFoundException : NotFoundException("해당 식당은 존재하지 않습니다.")
}
