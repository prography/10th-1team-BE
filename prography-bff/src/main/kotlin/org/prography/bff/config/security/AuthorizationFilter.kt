package org.prography.bff.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.time.LocalDateTime

class AuthorizationFilter(
    private val uriRoleMap: Map<String, List<String>>,
) : Filter {
    private val objectMapper = jacksonObjectMapper()

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain,
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        val path = httpRequest.requestURI
        val requiredRoles = getRequiredRoles(path)
        val userRole = request.getAttribute("role") as? String

        // 인가 확인
        val authorized = requiredRoles.isEmpty() || userRole in requiredRoles

        if (!authorized) {
            val errorBody =
                mapOf(
                    "error" to "접근 권한이 없는 유저입니다.",
                    "time" to LocalDateTime.now().toString(),
                )

            val json = objectMapper.writeValueAsString(errorBody)
            httpResponse.contentType = "application/json;charset=UTF-8"
            httpResponse.writer.write(json)
            return
        }

        chain.doFilter(request, response)
    }

    /**
     * 경로 기반 Role 매핑 조회
     * - URI가 시작되는 prefix 기준으로 매칭
     * - 가장 먼저 일치하는 엔트리를 반환
     */
    private fun getRequiredRoles(path: String): List<String> {
        return uriRoleMap.entries
            .find { path.startsWith(it.key) }
            ?.value ?: emptyList()
    }
}
