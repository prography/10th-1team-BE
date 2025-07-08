package org.prography.bff.user.service

import org.prography.bff.user.service.model.VoteActivity
import org.prography.bff.vote.repository.VoteCustomQueryRepositoryImpl
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class UserActivityService(
    private val voteQueryRepository: VoteCustomQueryRepositoryImpl,
) {
    fun getVoteActivities(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<VoteActivity> {
        val historyEntities: List<VoteHistoryEntity> = voteQueryRepository.findHistoriesByVotedDateBetween(userId, from, to)

        return historyEntities.map {
            VoteActivity(
                placeId = it.placeId,
                category = it.category,
                platform = it.platform.name,
                reasons =
                    it.reasons.map { reason ->
                        reason.name
                    },
                placeName = it.placeName,
                votedDate = it.votedDate,
            )
        }
    }

    fun getVoteActivities(userId: UUID): List<VoteActivity> {
        val historyEntities: List<VoteHistoryEntity> = voteQueryRepository.findHistories(userId)

        return historyEntities.map {
            VoteActivity(
                placeId = it.placeId,
                category = it.category,
                platform = it.platform.name,
                reasons =
                    it.reasons.map { reason ->
                        reason.name
                    },
                placeName = it.placeName,
                votedDate = it.votedDate,
            )
        }
    }
}
