package org.prography.bff.config.exception.notfound

sealed class NotFoundException(
    override val message: String,
) : RuntimeException(message) {
    /**
     * 장소를 찾지 못한 경우 발생하는 예외
     */
    class PlaceNotFoundException : NotFoundException("해당 식당은 존재하지 않습니다.")

    /**
     * 장소에 대한 정보를 찾지 못한 경우 발생하는 예외
     */
    class PlaceInfoNotFoundException : NotFoundException("해당 식당의 정보가 수집되지 않았습니다.")
}
