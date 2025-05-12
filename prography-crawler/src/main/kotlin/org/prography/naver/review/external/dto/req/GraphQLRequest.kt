package org.prography.naver.review.external.dto.req

/**
 * GraphQL 요청 페이로드
 *
 * @property operationName GraphQL operation 이름 (예: "getVisitorReviews")
 * @property variables     쿼리 변수 객체
 * @property query         GraphQL 쿼리 문자열
 */
data class GraphQLRequest<T>(
    val operationName: String,
    val variables: T,
    val query: String,
)
