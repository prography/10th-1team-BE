package org.prography.bff.vote.repository.model.enumeration

/**
 * 투표 서비스에서 지원하는 플랫폼 목록
 */
enum class VotePlatform {
    /**
     * 카카오 맵
     */
    KAKAO,

    /**
     * 네이버 맵
     */
    NAVER,

    /**
     * 아직 서비스되지 않는 플랫폼
     */
    UNDEFINED,
}
