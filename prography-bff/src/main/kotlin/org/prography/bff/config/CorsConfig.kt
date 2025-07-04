package org.prography.bff.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://api.reviewmatch.co.kr",
                "https://reviewmatch.co.kr",
            ) // 허용할 Origin
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowCredentials(true)
            .maxAge(3600)
    }
}
