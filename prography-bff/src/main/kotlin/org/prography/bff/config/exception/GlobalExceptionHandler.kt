package org.prography.bff.config.exception

import org.prography.bff.config.exception.badrequest.InvalidRequestException
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
}
