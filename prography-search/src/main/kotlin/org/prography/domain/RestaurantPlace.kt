package org.prography.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Elasticsearch 에 저장되어 매핑되어 있는 문서 그대로 매핑된 도메인
 */
data class RestaurantPlace(
    /**
     * MongoDB 고유 아이디 (=_id)
     */
    val id: String,
    /**
     * 지역구 코드
     */
    val dongCode: String = "",
    /**
     * 카카오 주소
     */
    val kakaoAddress: String = "",
    /**
     * 네이버 주소
     */
    val naverAddress: String = "",
    /**
     * 카카오 도로명 주소
     */
    val kakaoRoadAddress: String = "",
    /**
     * 네이버 도로명 주소
     */
    val naverRoadAddress: String = "",
    /**
     * 카카오 상호명
     */
    val kakaoPlaceName: String = "",
    /**
     * 네이버 상호명
     */
    val naverPlaceName: String = "",
    /**
     * 합쳐진 카테고리 리스트
     */
    val category: List<String> = emptyList(),
    /**
     * 네이버 가게 정보의 대표 이미지 URL
     */
    val imageUrl: String = "",
    /**
     * 네이버 가게 정보의 블로그 리뷰 갯수
     */
    val blogReviewCount: Long = 0,
    /**
     * 네이버 가게 정보의 방문자 리뷰 갯수
     */
    val visitReviewCount: Long = 0,
    /**
     * 카카오 리뷰 갯수
     */
    val kakaoReviewCount: Long = 0,
    /**
     * 카카오 리뷰 평균 점수
     */
    val kakaoScore: Float = 0.0f,
    /**
     * 크롤러의 카카오 리뷰 수집 진행 여부
     */
    val kakaoReview: Boolean = false,
    /**
     * 네이버 리뷰 갯수
     */
    val naverReviewCount: Long = 0,
    /**
     * 네이버 리뷰 평균 점수
     */
    val naverScore: Float = 0.0f,
    /**
     * 크롤러의 네이버 리뷰 수집 진행 여부
     */
    val naverReview: Boolean = false,
    /**
     * MongoDB oplog에서 동기화된 이 문서의 최종 수정 일시.
     * "yyyy/MM/dd HH:mm:ss" 형식으로 저장됩니다.
     */
    @JsonProperty("oplog_date")
    val oplogDate: String = "",
    /**
     * MongoDB oplog 타임스탬프 객체.
     * - T: 초 단위 Unix 타임스탬프
     * - I: 동일 초 내에서의 증분 카운터
     */
    @JsonProperty("oplog_ts")
    val oplogTs: OplogTs = OplogTs(),
)
