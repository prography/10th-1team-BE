package org.prography.bff.restaurant.controller.model.review

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReviewDtoDateFormatterTest {
    @Test
    @DisplayName("parseKakaoToLocalDateTime 정상 변환")
    fun testParseKakao_Valid() {
        val input = "2025-04-01 20:08:21"
        val expected =
            LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        val actual = ReviewDateFormatter.parseKakaoToLocalDateTime(input)
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("parseKakaoToLocalDateTime 변환 실패 시 예외 발생")
    fun testParseKakao_Invalid() {
        val input = "invalid-date"
        assertThrows<IllegalArgumentException> {
            ReviewDateFormatter.parseKakaoToLocalDateTime(input)
        }
    }

    @Test
    @DisplayName("parseNaverToLocalDateTime 2자리 연도 포함 날짜 정상 변환")
    fun testParseNaver_TwoDigitYear() {
        val input = "24.12.19.목"
        val expectedDate = LocalDate.parse("24.12.19", DateTimeFormatter.ofPattern("yy.MM.dd"))
        val expected = expectedDate.atStartOfDay()

        val actual = ReviewDateFormatter.parseNaverToLocalDateTime(input)
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("parseNaverToLocalDateTime 월일 날짜 형식 (연도 추정) 정상 변환")
    fun testParseNaver_MonthDay() {
        val fallbackYear = 2025
        val input = "5.13.화"
        val expectedDate =
            LocalDate.parse("$fallbackYear-05-13", DateTimeFormatter.ofPattern("uuuu-MM-dd"))
        val expected = expectedDate.atStartOfDay()

        val actual = ReviewDateFormatter.parseNaverToLocalDateTime(input, fallbackYear)
        assertEquals(expected, actual)
    }

    @Test
    @DisplayName("parseNaverToLocalDateTime 이상한 포맷 입력 시 예외 발생")
    fun testParseNaver_InvalidFormat() {
        val input = "random string"
        assertThrows<IllegalArgumentException> {
            ReviewDateFormatter.parseNaverToLocalDateTime(input)
        }
    }

    @Test
    @DisplayName("parseNaverToLocalDateTime 빈 문자열 입력 시 예외 발생")
    fun testParseNaver_EmptyString() {
        val input = ""
        assertThrows<IllegalArgumentException> {
            ReviewDateFormatter.parseNaverToLocalDateTime(input)
        }
    }

    @Test
    fun `parseNaverToLocalDateTime - 정상 포맷 2자리 연도, 월, 1자리 일`() {
        val raw = "24.10.5.토"
        val result = ReviewDateFormatter.parseNaverToLocalDateTime(raw)
        assertEquals(LocalDateTime.of(2024, 10, 5, 0, 0), result)
    }

    @Test
    fun `parseNaverToLocalDateTime - 정상 포맷 2자리 연도, 1자리 월, 일`() {
        val raw = "24.8.5.토"
        val result = ReviewDateFormatter.parseNaverToLocalDateTime(raw)
        assertEquals(LocalDateTime.of(2024, 8, 5, 0, 0), result)
    }
}
