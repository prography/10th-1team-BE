package org.prography.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        // API 기본 정보
        val info =
            Info()
                .title("Review:Match API")
                .version("v1.0.0")
                .description("Review:Match BFF 서버 API 문서")
                .summary("요청 성공 시의 예시 값은 내부 데이터 기준입니다!")

        return OpenAPI()
            .info(info)
    }

    // Snake Case 적용
    @Bean
    fun modelResolver(objectMapper: ObjectMapper): ModelResolver {
        return ModelResolver(objectMapper)
    }
}
