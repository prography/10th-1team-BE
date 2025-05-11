package org.prography.config.response

import java.time.LocalDateTime

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T, val time: LocalDateTime = LocalDateTime.now()) :
        ApiResponse<T>()

    data class Failure(val error: String, val time: LocalDateTime = LocalDateTime.now()) :
        ApiResponse<Nothing>()

    companion object {
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun fail(message: String): ApiResponse<Nothing> = Failure(message)
    }
}