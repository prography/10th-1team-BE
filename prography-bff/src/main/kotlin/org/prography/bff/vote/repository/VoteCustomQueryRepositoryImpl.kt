package org.prography.bff.vote.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.prography.bff.vote.repository.custom.VoteCustomQueryRepository
import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class VoteCustomQueryRepositoryImpl(
    private val kotlinJpqlExecutor: KotlinJdslJpqlExecutor,
    private val voteRepository: VoteEntityRepository,
    private val historyRepository: VoteHistoryEntityRepository,
) : VoteCustomQueryRepository {
    override fun findById(id: VoteId): Optional<VoteEntity> {
        return voteRepository.findById(id)
    }

    override fun findByPlaceId(placeId: String): Map<VotePlatform, VoteEntity> {
        val entities: List<VoteEntity> =
            kotlinJpqlExecutor.findAll {
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
}
