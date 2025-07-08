package org.prography.bff.vote.repository

import org.prography.bff.vote.repository.custom.VoteCustomCmdRepository
import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.springframework.stereotype.Repository

@Repository
class VoteCustomCmdRepositoryImpl(
    private val voteRepository: VoteEntityRepository,
    private val historyEntityRepository: VoteHistoryEntityRepository,
) : VoteCustomCmdRepository {
    override fun save(vote: VoteEntity) {
        voteRepository.save(vote)
    }

    override fun save(history: VoteHistoryEntity) {
        historyEntityRepository.save(history)
    }

    override fun deleteHistory(history: VoteHistoryEntity) {
        historyEntityRepository.delete(history)
    }
}
