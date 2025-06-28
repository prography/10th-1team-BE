package org.prography.bff.config.exception.invaildformat

sealed class InvalidFormatException(
    override val message: String,
) : RuntimeException(message) {
    class InvalidKakaoDateFormat(raw: String) :
        InvalidFormatException("지원하지 않는 카카오 날짜 형식입니다: $raw")

    class InvalidNaverDateFormat(raw: String) :
        InvalidFormatException("지원하지 않는 네이버 날짜 형식입니다: $raw")
}
