package org.prography.bff.config.security

/**
 * 컨트롤러 메서드의 파라미터에 붙여,
 * JWT 필터에서 request attribute로 저장한 사용자 ID(UUID)를 주입받기 위한 어노테이션입니다.
 *
 * 사용 예:
 * ```
 * fun getProfile(@AuthUser userId: UUID): ResponseEntity<...>
 * ```
 *
 * 해당 파라미터는 UUID 타입이어야 하며,
 * 필터에서 `request.setAttribute("userId", userId)`로 저장된 값이 필요합니다.
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthUser
