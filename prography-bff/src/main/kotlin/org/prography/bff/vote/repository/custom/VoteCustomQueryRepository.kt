package org.prography.bff.vote.repository.custom

import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.util.Optional

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
}
