package org.prography.bff.vote.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.prography.bff.vote.repository.model.converter.VoteCategoriesConverter
import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
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
    var id: Long? = null,
    /**
     * 유저 PK
     */
    @Column(name = "user_id", nullable = false)
    var userId: UUID,
    /**
     * 가게 PK
     */
    @Column(name = "place_id", nullable = false)
    var placeId: String,
    /**
     * 음식점 유형
     */
    @Column(name = "place_name")
    var placeName: String,
    /**
     * 투표 이유
     */
    @Column(name = "reasons", nullable = false)
    @Convert(converter = VoteCategoriesConverter::class)
    var reaons: List<VoteCategory> = emptyList(),
    /**
     * 투표한 플랫폼
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    var platform: VotePlatform,
    /**
     * 음식점 유형
     */
    @Column(name = "category")
    var category: String,
    /**
     * 투표한 시간
     */
    @LastModifiedDate
    @Column(name = "voted_date", nullable = false)
    var votedDate: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VoteHistoryEntity) return false
        return userId == other.userId && placeId == other.placeId
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + placeId.hashCode()
        return result
    }

    override fun toString(): String = "VoteHistoryEntity(userId=$userId, placeId='$placeId')"
}
