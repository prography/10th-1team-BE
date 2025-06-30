package org.prography.bff.user.service.model

import java.time.LocalDateTime

/**
 * 유저의 투표 활동 데이터
 */
data class VoteActivity(
    /**
     * 투표한 가게의 대한 아이디
     */
    val placeId: String,
    /**
     * 투표한 가게의 음식점 유형
     */
    val category: String,
    /**
     * 투표한 플랫폼
     */
    val platform: String,
    /**
     * 투표한 이유들
     */
    val reasons: List<String> = emptyList(),
    /**
     * 가게의 이름
     */
    val placeName: String,
    /**
     * 투표된 날짜
     */
    val votedDate: LocalDateTime,
)
