package org.prography.bff.search.controller.model

/**
 * 리뷰 요약 정보
 */
data class ReviewSummary(
    /**
     * 리뷰 갯수
     */
    val count: Long,
    /**
     * 리뷰 평균 점수
     */
    val score: Float,
    /**
     * 리뷰 수집 여부
     * true 인데 count 및 score 0 일 경우 리뷰가 없음을 뜻함
     */
    val processed: Boolean,
)
