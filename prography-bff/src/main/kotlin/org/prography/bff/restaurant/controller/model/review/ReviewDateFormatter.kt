package org.prography.bff.restaurant.controller.model.review

import org.prography.bff.config.exception.invaildformat.InvalidFormatException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object ReviewDateFormatter {
    private val kakaoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val naverShortFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")
    private val fallbackFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")

    fun parseKakaoToLocalDateTime(raw: String): LocalDateTime {
        return try {
            LocalDateTime.parse(raw, kakaoFormatter)
        } catch (e: Exception) {
            throw InvalidFormatException.InvalidKakaoDateFormat(raw)
        }
    }

    fun parseNaverToLocalDateTime(
        raw: String,
        fallbackYear: Int = LocalDateTime.now().year,
    ): LocalDateTime {
        val trimmed = raw.trim().removeSuffix(".")
        return try {
            when {
                raw.matches(Regex("""\d{2}\.\d{1,2}\.\d{1,2}\..*""")) -> {
                    val dateStr = trimmed.substringBeforeLast(".")
                    val parts = dateStr.split(".")
                    val year = parts[0].padStart(2, '0')
                    val month = parts[1].padStart(2, '0')
                    val day = parts[2].padStart(2, '0')
                    val localDate = LocalDate.parse("$year.$month.$day", naverShortFormatter)
                    localDate.atStartOfDay()
                }

                raw.matches(Regex("""\d{1,2}\.\d{1,2}\..*""")) -> {
                    val parts = trimmed.split(".")
                    val month = parts[0].padStart(2, '0')
                    val day = parts[1].padStart(2, '0')
                    val localDate = LocalDate.parse("$fallbackYear-$month-$day", fallbackFormatter)
                    localDate.atStartOfDay()
                }

                else -> throw InvalidFormatException.InvalidNaverDateFormat(raw)
            }
        } catch (e: Exception) {
            throw InvalidFormatException.InvalidNaverDateFormat(raw)
        }
    }
}
