package org.prography.bff.vote.repository.custom

import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteHistoryEntity

/**
 * 투표 관련 DB 저장 및 수정 인터페이스
 */
interface VoteCustomCmdRepository {
    /**
     * VoteEntity 저장
     */
    fun save(vote: VoteEntity)

    /**
     * VoteHistoryEntity 저장
     */
    fun save(history: VoteHistoryEntity)
}
