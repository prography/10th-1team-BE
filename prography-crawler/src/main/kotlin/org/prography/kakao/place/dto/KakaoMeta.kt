package org.prography.kakao.place.dto

/**
 * Kakao API에서 반환되는 메타 정보 DTO
 */
data class KakaoMeta(
    /** 마지막 페이지 여부 */
    var is_end: Boolean,
    /** 요청 가능한 페이지 수 */
    var pageable_count: Int,
    /** 전체 문서 수 */
    var total_count: Int,
)
