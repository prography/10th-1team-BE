package org.prography.bff.vote.controller.model.enumeration

import io.swagger.v3.oas.annotations.media.Schema

/**
 * 투표가 가능한 플랫폼 목록
 */
@Schema(description = "플랫폼 종류")
enum class MatchPlatform {
    /**
     * 카카오 맵 리뷰
     */
    @Schema(description = "카카오")
    KAKAO,

    /**
     * 네이버 맵 리뷰
     */
    @Schema(description = "네이버 ")
    NAVER,
}
