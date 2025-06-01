package org.prography.search.controller.model.enumeration

/**
 * 정렬 방식
 */
enum class OrderStrategy {
    AVERAGE_RATING_HIGH, // 별점 높은 순 (카카오+네이버 평균)
    AVERAGE_RATING_LOW, // 별점 낮은 순 (카카오+네이버 평균)
    REVIEW_COUNT_HIGH, // 리뷰 많은 순 (카카오+네이버 합산)
    REVIEW_COUNT_LOW, // 리뷰 적은 순 (카카오+네이버 합산)

    KAKAO_RATING_HIGH, // 카카오 별점 높은 순
    KAKAO_RATING_LOW, // 카카오 별점 낮은 순
    KAKAO_REVIEW_COUNT_HIGH, // 카카오 리뷰 많은 순

    NAVER_RATING_HIGH, // 네이버 별점 높은 순
    NAVER_RATING_LOW, // 네이버 별점 낮은 순
    NAVER_REVIEW_COUNT_HIGH, // 네이버 리뷰 많은 순
}
