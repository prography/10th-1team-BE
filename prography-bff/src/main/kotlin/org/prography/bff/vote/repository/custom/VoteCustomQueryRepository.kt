package org.prography.bff.vote.repository.custom

import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

/**
 * 투표 관련 DB 조회 인터페이스
 */
interface VoteCustomQueryRepository {
    /**
     * 상호명 + 플랫폼 복합키로 VoteEntity 조회
     */
    fun findById(id: VoteId): Optional<VoteEntity>

    /**
     * 상호명 만으로 지원되는 모든 플랫폼에 대한 VoteEntity 조회
     */
    fun findByPlaceId(placeId: String): Map<VotePlatform, VoteEntity>

    /**
     * 상호명 + 플랫폼 복합키로 VetEntity 유무 판단
     */
    fun existsById(id: VoteId): Boolean

    /**
     * 상호명 + 유저 ID 기반으로 유저 투표 여부 판단
     */
    fun existsUserVote(
        userId: UUID,
        placeId: String,
    ): Boolean

    /**
     * 특정 기간(from - to) 사이에 해당 유저의 투표 이력 조회
     */
    fun findHistoriesByVotedDateBetween(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<VoteHistoryEntity>

    /**
     * 유저의 아이디와 가게 아이디를 통해서 투표 내역 조회
     */
    fun findHistory(
        userId: UUID,
        placeId: String,
    ): VoteHistoryEntity?

    /**
     * 유저의 아이디를 통해서 투표 내역 조회
     */
    fun findHistories(userId: UUID): List<VoteHistoryEntity>
}
