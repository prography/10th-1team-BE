package org.prography.naver.review.external.dto.req

/**
 * GraphQL 변수 `input` 객체
 *
 * @property businessId   네이버 비즈니스 ID (예: 1876859078)
 * @property businessType 비즈니스 유형 (예: "restaurant")
 * @property page         페이지 번호 (기본 1)
 * @property size         페이지 크기 (기본 50)
 */
data class VisitorReviewsInput(
    val businessId: String,
    val businessType: String,
    val page: Int,
    val size: Int,
)
