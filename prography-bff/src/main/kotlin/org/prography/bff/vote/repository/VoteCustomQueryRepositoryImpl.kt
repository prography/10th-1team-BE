package org.prography.bff.vote.repository

import org.prography.bff.vote.repository.custom.VoteCustomQueryRepository
import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Repository
class VoteCustomQueryRepositoryImpl(
    private val voteRepository: VoteEntityRepository,
    private val historyRepository: VoteHistoryEntityRepository,
) : VoteCustomQueryRepository {
    override fun findById(id: VoteId): Optional<VoteEntity> {
        return voteRepository.findById(id)
    }

    override fun findByPlaceId(placeId: String): Map<VotePlatform, VoteEntity> {
        val entities: List<VoteEntity> =
            voteRepository.findAll {
                select(entity(VoteEntity::class))
                    .from(entity(VoteEntity::class))
                    .where(
                        path(VoteEntity::id)(VoteId::placeId).eq(placeId),
                    )
            }.filterNotNull()

        return entities.associateBy { it.id.platform }
    }

    override fun existsById(id: VoteId): Boolean {
        return voteRepository.existsById(id)
    }

    override fun existsUserVote(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return historyRepository.findAll(limit = 1) {
            select(intLiteral(1))
                .from(entity(VoteHistoryEntity::class))
                .where(
                    path(VoteHistoryEntity::userId).eq(userId).and(
                        path(VoteHistoryEntity::placeId).eq(placeId),
                    ),
                )
        }.isNotEmpty()
    }

    override fun findHistoriesByVotedDateBetween(
        userId: UUID,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<VoteHistoryEntity> {
        return historyRepository.findAll {
            select(entity(VoteHistoryEntity::class))
                .from(entity(VoteHistoryEntity::class))
                .where(
                    path(VoteHistoryEntity::votedDate).between(from, to)
                        .and(path(VoteHistoryEntity::userId).equal(userId)),
                )
        }.filterNotNull()
    }
}
