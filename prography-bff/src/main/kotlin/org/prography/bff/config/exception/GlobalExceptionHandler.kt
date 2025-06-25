package org.prography.bff.config.exception

import org.prography.bff.config.exception.badrequest.InvalidRequestException
import org.prography.bff.config.exception.external.ExternalApiException
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.config.response.ApiResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequest(e: InvalidRequestException): ResponseEntity<ApiResponse<Nothing>> {
        log.warn("InvalidRequestException: ${e.message}")
        return ResponseEntity
            .badRequest()
            .body(ApiResponse.fail(e.message))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        log.warn("NotFoundException: ${e.message}")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.fail(e.message))
    }

    @ExceptionHandler(ExternalApiException::class)
    fun handleExternalApiError(e: ExternalApiException): ResponseEntity<ApiResponse<Nothing>> {
        log.error("ExternalApiException: ${e.message}")
        return ResponseEntity
            .status(e.status)
            .body(ApiResponse.fail(e.message))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(e: Exception): ResponseEntity<ApiResponse<Nothing>> {
        log.error("Unhandled exception: ${e.message}", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.fail("알 수 없는 서버 오류가 발생했습니다."))
    }
}
