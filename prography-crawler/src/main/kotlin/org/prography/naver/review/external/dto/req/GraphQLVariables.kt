package org.prography.naver.review.external.dto.req

/**
 * GraphQL `variables` 객체
 *
 * @property input 실제 쿼리에서 참조되는 `VisitorReviewsInput`
 */
data class GraphQLVariables(
    val input: VisitorReviewsInput? = null,
)
