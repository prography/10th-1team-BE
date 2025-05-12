package org.prography.naver.review.external.dto.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * 네이버 GraphQL 리뷰 응답 래퍼
 *
 * @property data 실제 GraphQL 데이터 (`visitorReviews` 등)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class NaverReviewResponse(
    val data: GraphQLData, // = null → 무인자 생성자 확보
)
