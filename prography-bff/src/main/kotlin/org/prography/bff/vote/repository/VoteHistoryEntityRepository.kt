package org.prography.bff.vote.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * VoteHistoryEntity 관련 Spring Data JPA + Kotlin JDSL 인터페이스
 */
interface VoteHistoryEntityRepository :
    JpaRepository<VoteHistoryEntity, Long>,
    KotlinJdslJpqlExecutor {
    fun existsByUserIdAndPlaceId(
        userId: UUID,
        placeId: String,
    ): Boolean
}
