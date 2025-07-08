package org.prography.bff.config.exception.notfound

sealed class NotFoundException(
    override val message: String,
) : RuntimeException(message) {
    /**
     * 장소를 찾지 못한 경우 발생하는 예외
     */
    class PlaceNotFoundException : NotFoundException("해당 식당은 존재하지 않습니다.")

    /**
     * 장소에 대한 적절한 정보를 찾지 못한 경우 발생하는 예외
     */
    class PlaceInfoNotFoundException : NotFoundException("해당 식당의 정보가 수집되지 않았습니다.")

    class ProviderNotFoundException : NotFoundException("지원하지 않는 제공자입니다.")

    class UserNotFoundException : NotFoundException("유저 정보가 없습니다.")

    class DongNotFoundException : NotFoundException("코드에 해당하는 법정동이 없습니다")

    class VoteHistoryNotFoundException : NotFoundException("해당 투표 이력이 존재하지 않습니다.")

    class VoteNotFoundException : NotFoundException("플랫폼 투표가 존재하지 않습니다.")
}
