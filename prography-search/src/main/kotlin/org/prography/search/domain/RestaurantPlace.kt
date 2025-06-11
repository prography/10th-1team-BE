package org.prography.search.domain

/**
 * Elasticsearch 에 저장되어 매핑되어 있는 문서 그대로 매핑된 도메인
 */
data class RestaurantPlace(
    /** MongoDB 고유 아이디 (=_id) */
    val id: String,
    /** 행정구 코드 */
    val division: String = "",
    /** 법정동 코드 */
    val legal: String = "",
    /** 주소 (카카오 기반) */
    val address: String = "",
    /** 도로명 주소 (카카오 기반) */
    val roadAddress: String = "",
    /** 상호명 (카카오 기반) */
    val placeName: String = "",
    /** 이미지 존재 여부 */
    val image: Boolean = false,
    /** 대표 이미지 URL */
    val imageUrl: String = "",
    /** 카카오 리뷰 갯수 */
    val kakaoReviewCount: Long = 0L,
    /** 카카오 리뷰 평균 점수 */
    val kakaoScore: Double = 0.0,
    /** 카카오 리뷰 수집 여부 */
    val kakaoReview: Boolean = false,
    /** 네이버 리뷰 갯수 */
    val naverReviewCount: Long = 0L,
    /** 네이버 리뷰 평균 점수 */
    val naverScore: Double = 0.0,
    /** 네이버 리뷰 수집 여부 */
    val naverReview: Boolean = false,
    /** 합산 리뷰 갯수 */
    val reviewCount: Long = 0L,
    /** 평균 리뷰 점수 */
    val reviewScore: Double = 0.0,
    /** 위치 정보 */
    val location: GeoPoint? = null,
    /**
     * MongoDB oplog에서 동기화된 이 문서의 최종 수정 일시
     * "yyyy/MM/dd HH:mm:ss" 형식으로 저장
     */
    val oplogDate: String = "",
    /**
     * MongoDB oplog 타임스탬프 객체.
     * - T: 초 단위 Unix 타임스탬프
     * - I: 동일 초 내에서의 증분 카운터
     */
    val oplogTs: OplogTs = OplogTs(),
)
