package org.prography.config.exception.notfound

sealed class NotFoundException(
    override val message: String,
) : RuntimeException(message) {
    class PlaceNotFoundException : NotFoundException("해당 식당은 존재하지 않습니다.")

    class PlaceInfoNotFoundException : NotFoundException("해당 식당의 정보가 수집되지 않았습니다.")
}
