package org.prography.bff.vote.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.Objects
import java.util.UUID

/**
 * 투표 이력 엔티티
 */
@Entity
@Table(name = "VOTE_HISTORY")
@EntityListeners(AuditingEntityListener::class)
class VoteHistoryEntity(
    /**
     * AUTO INCREASE
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long = 0L,
    /**
     * 유저 PK
     */
    @Column(name = "user_id", nullable = false)
    val userId: UUID,
    /**
     * 가게 PK
     */
    @Column(name = "place_id", nullable = false)
    val placeId: String,
    /**
     * 투표 이유
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: VoteCategory,
    /**
     * 투표한 플랫폼
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    val platform: VotePlatform,
    /**
     * 투표한 시간
     */
    @LastModifiedDate
    @Column(name = "voted_date", nullable = false)
    var votedDate: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean = Objects.equals(id, (other as? VoteHistoryEntity)?.id)

    override fun hashCode(): Int = Objects.hashCode(id)

    override fun toString(): String = "VoteHistory(id=$id)"
}
