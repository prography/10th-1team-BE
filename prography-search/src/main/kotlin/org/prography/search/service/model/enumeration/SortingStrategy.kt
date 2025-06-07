package org.prography.search.service.model.enumeration

/**
 * Elasticsearch 결과 정렬 전략
 */
enum class SortingStrategy(val sorting: String) {
    AVERAGE_RATING_HIGH("avgRating,desc"), // 별점 높은 순 (카카오+네이버 평균)
    AVERAGE_RATING_LOW("avgRating,asc"), // 별점 낮은 순 (카카오+네이버 평균)
    REVIEW_COUNT_HIGH("totalReviewCount,desc"), // 리뷰 많은 순 (카카오+네이버 합산)
    REVIEW_COUNT_LOW("totalReviewCount,asc"), // 리뷰 적은 순 (카카오+네이버 합산)

    KAKAO_RATING_HIGH("kakaoScore,desc"), // 카카오 별점 높은 순
    KAKAO_RATING_LOW("kakaoScore,asc"), // 카카오 별점 낮은 순
    KAKAO_REVIEW_COUNT_HIGH("kakaoReviewCount,desc"), // 카카오 리뷰 많은 순

    NAVER_RATING_HIGH("naverScore,desc"), // 네이버 별점 높은 순
    NAVER_RATING_LOW("naverScore,asc"), // 네이버 별점 낮은 순
    NAVER_REVIEW_COUNT_HIGH("naverReviewCount,desc"), // 네이버 리뷰 많은 순
}
