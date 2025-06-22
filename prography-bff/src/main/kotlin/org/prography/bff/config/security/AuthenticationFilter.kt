package org.prography.bff.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

class AuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : Filter {
    private val objectMapper = jacksonObjectMapper()

    /**
     * Token이 존재하며 유효하다면 Request에 payload 저장,
     * 없다면 다음 필터로
     */
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val token = extractToken(httpRequest)

        if (token != null && !jwtProvider.validateToken(token)) {
            val errorBody =
                mapOf(
                    "error" to "잘못된 토큰 형식입니다.",
                    "time" to LocalDateTime.now().toString(),
                )

            val json = objectMapper.writeValueAsString(errorBody)
            httpResponse.contentType = "application/json;charset=UTF-8"
            httpResponse.status = HttpServletResponse.SC_UNAUTHORIZED
            httpResponse.writer.write(json)
            return
        }

        // 토큰이 존재하는 경우에 주입
        token?.let {
            val userId = jwtProvider.getUserId(token)
            request.setAttribute("userId", userId)
            request.setAttribute("role", "USER")
        }

        chain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val header = request.getHeader("Authorization")
        return if (header != null && header.startsWith("Bearer ")) {
            header.substring(7)
        } else {
            null
        }
    }
}
