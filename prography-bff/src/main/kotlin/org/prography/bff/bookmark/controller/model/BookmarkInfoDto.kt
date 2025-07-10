package org.prography.bff.bookmark.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

/**
 * 저장된 가게 조회 DTO
 */
@Schema(title = "저장된 가게 조회 DTO", description = "그룹을 통해서 자장된 가게를 조회할 경우의 DTO")
data class BookmarkInfoDto(
    /**
     *
     */
    @Schema(title = "북마크 아이디", description = "북마크 고유 아이디")
    val id: UUID,
    /**
     * 저장된 가게 고유 아이디
     */
    @Schema(title = "가게 고유 아이디", description = "저장된 가게의 상세 페이지 조회를 위한 고유 아이디")
    val placeId: String,
    /**
     * 저장된 가게의 상호명
     */
    @Schema(title = "가게명", description = "저장된 가게 상호명")
    val placeName: String,
    /**
     * 저장된 가게의 도로명 주소
     */
    @Schema(title = "도로명", description = "저장된 가게 도로명 주소")
    val roadAddress: String,
    /**
     * 음식점의 유형
     */
    @Schema(title = "음식점 유형", description = "저장된 가게 분류된 유형")
    val category: String,
    /**
     * 법정동 이름
     */
    @Schema(title = "법정동", description = "저장된 가게의 도로명 기반 법정동 이름")
    val legal: Int,
    /**
     * 저장된 날짜
     */
    @Schema(title = "저장 날짜", description = "그룹에 가게의 저장된 날짜")
    val savedAt: LocalDateTime,
)
