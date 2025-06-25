package org.prography.bff.config.exception.badrequest

sealed class InvalidRequestException(
    override val message: String,
) : RuntimeException(message) {
    /**
     * 투표 이유가 비어있을 경우 발생하는 예외
     */
    class ReasonEmpty : InvalidRequestException("투표 이유는 반드시 한 개 이상을 입력하셔야 합니다.")
}
