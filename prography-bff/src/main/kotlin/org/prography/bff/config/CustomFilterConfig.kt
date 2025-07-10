package org.prography.bff.config

import org.prography.bff.config.security.AuthenticationFilter
import org.prography.bff.config.security.AuthorizationFilter
import org.prography.bff.config.security.JwtProvider
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomFilterConfig {
    @Bean
    fun authenticationFilter(jwtProvider: JwtProvider): FilterRegistrationBean<AuthenticationFilter> {
        val registration = FilterRegistrationBean(AuthenticationFilter(jwtProvider))
        registration.addUrlPatterns("/*")
        registration.order = 1
        return registration
    }

    /**
     * Security URI에 대한 권한을 결정
     */
    @Bean
    fun authorizationFilter(): FilterRegistrationBean<AuthorizationFilter> {
        val uriRoleMap =
            mapOf(
                "/users/me" to listOf("USER"),
                "/users/activity" to listOf("USER"),
                "/group" to listOf("USER"),
                "/vote/submit" to listOf("USER"),
                "/vote/cancel" to listOf("USER"),
                "/bookmark" to listOf("USER"),
            )

        return FilterRegistrationBean(AuthorizationFilter(uriRoleMap)).apply {
            order = 2
            addUrlPatterns("/*")
        }
    }
}
