package org.prography.bff.voting.service

import org.prography.bff.voting.repository.VoteQueryRepository
import org.prography.bff.voting.repository.VotingCmdRepository

/**
 * 투표 관련한 서비스
 */
class VoteService(
    private val voteQueryRepository: VoteQueryRepository,
    private val voteCmdRepository: VotingCmdRepository,
) {
    /**
     * 투표 하기
     */
    fun submit(placeId: String) {
    }

    /**
     * 투표 내용 수정
     */
    fun modify(placeId: String) {
    }

    /**
     * 투표 취소
     */
    fun cancel(placeId: String) {
    }

    /**
     * 투표 결과 반환
     */
    fun getVoteResult(placeId: String) {
    }

    /**
     * 사용자 투표 이력
     */
    fun getSubmitHistory(userId: String) {
    }
}
