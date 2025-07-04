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

    /**
     * 정의되지 않은 플랫폼
     */
    @Schema(description = "정의되지 않음")
    UNDEFINED,

    ;

    companion object {
        fun fromString(value: String?): MatchPlatform {
            return try {
                value?.trim()?.uppercase()?.let { upper ->
                    entries.firstOrNull { it.name == upper } ?: UNDEFINED
                } ?: UNDEFINED
            } catch (e: Exception) {
                UNDEFINED
            }
        }
    }
}
