package org.prography.bff.search.controller.model.enumeration

/**
 * 정렬 방식
 */
enum class OrderStrategy {
    RELATED,
    AVERAGE_RATING_HIGH, // 별점 높은 순 (카카오+네이버 평균)
    AVERAGE_RATING_LOW, // 별점 낮은 순 (카카오+네이버 평균)
    REVIEW_COUNT_HIGH, // 리뷰 많은 순 (카카오+네이버 합산)
    REVIEW_COUNT_LOW, // 리뷰 적은 순 (카카오+네이버 합산)
}
