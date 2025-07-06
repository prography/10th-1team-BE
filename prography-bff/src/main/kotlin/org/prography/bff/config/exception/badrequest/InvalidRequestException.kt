package org.prography.bff.config.exception.badrequest

sealed class InvalidRequestException(
    override val message: String,
) : RuntimeException(message) {
    class InvalidTokenException : InvalidRequestException("유저 ID를 찾지 못했습니다.")

    /**
     * 투표 이유가 비어있을 경우 발생하는 예외
     */
    class ReasonEmpty : InvalidRequestException("투표 이유는 반드시 한 개 이상을 입력하셔야 합니다.")

    /**
     * 탈퇴한 유저일 경우 예외
     */
    class WithDrawUserException : InvalidRequestException("탈퇴한 유저는 활성화가 필요합니다.")

    /**
     * 이미 투표한 이럭이 있을 경우 발생하는 예외
     */
    class AlreadyVoted : InvalidRequestException("해당 유저는 이미 투표한 이력이 있습니다.")

    /**
     * 유저가 가지고 있는 그룹에 동일한 그룹이 있습니다.
     */
    class AlreadyGroup : InvalidRequestException("동일한 이름의 그룹을 가지고 있습니다.")
}
