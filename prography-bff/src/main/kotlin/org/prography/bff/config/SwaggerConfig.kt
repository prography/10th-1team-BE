package org.prography.bff.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.prography.bff.config.security.AuthUser
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod

// Todo : Swagger UI에 JWT Bearer 입력 필드를 추가하는 설정, 로그인 구현 후 적용
// @SecurityScheme(
//    name = "BearerAuth",
//    type = SecuritySchemeType.HTTP,
//    scheme = "bearer",
//    bearerFormat = "JWT",
//    `in` = SecuritySchemeIn.HEADER,
//    paramName = "Authorization"
// )

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

        // HTTPS 서버를 명시
        val httpsServer =
            Server()
                .url("https://api.reviewmatch.co.kr")
                .description("Production HTTPS 서버")

        val localServer =
            Server()
                .url("http://localhost:8080")
                .description("Local 서버")

        // JWT Security Scheme 정의
        val jwtSecurityScheme =
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .`in`(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("JWT Bearer Token")

        return OpenAPI()
            .info(info)
            .servers(listOf(httpsServer, localServer))
            .components(
                Components().addSecuritySchemes("JWT", jwtSecurityScheme),
            )
            .addSecurityItem(
                SecurityRequirement().addList("JWT"),
            )
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper) = ModelResolver(objectMapper)

    /**
     * 유저 커스텀 어노테이션을 스웨거가 인지하지 못하도록 수정
     */
    @Bean
    fun ignoreAuthenticatedUserAnnotation(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->

            // Java 리플렉션으로 실제 파라미터 확인
            val methodParameters = handlerMethod.method.parameters

            val filteredParameters =
                operation.parameters?.filterIndexed { index, _ ->
                    val param = methodParameters.getOrNull(index)
                    // @AuthUser 어노테이션이 붙지 않은 경우만 유지
                    param?.getAnnotation(AuthUser::class.java) == null
                }

            operation.parameters = filteredParameters
            operation
        }
    }
}
