package org.prography.bff.bookmark.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

/**
 * 북마크 그룹에 들어가는 가게
 */
@Entity
@Table(name = "BOOKMARK")
@EntityListeners(AuditingEntityListener::class)
class BookmarkEntity protected constructor() {
    /**
     * 유일성을 위한 UUID PK
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(
        name = "id",
        columnDefinition = "UUID",
        updatable = false,
        nullable = false,
    )
    lateinit var id: UUID

    /**
     * 해당 가게를 저장한 유저의 PK
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    lateinit var userId: UUID

    /**
     * 저장된 가게에 속해있는 그룹의 PK
     */
    @Column(name = "group_id", nullable = false, updatable = false)
    lateinit var groupId: UUID

    /**
     * 저장된 가게의 PK
     */
    @Column(name = "place_id", nullable = false, updatable = false)
    lateinit var placeId: String

    /**
     * 저장한 날짜
     */
    @CreatedDate
    @Column(name = "saved_at")
    lateinit var savedAt: LocalDateTime

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BookmarkEntity) return false
        return userId == other.userId && groupId == other.groupId && placeId == other.placeId
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + groupId.hashCode()
        result = 31 * result + placeId.hashCode()
        return result
    }

    override fun toString(): String = "BookmarkEntity(userId=$userId, groupId=$groupId, placeId=$placeId)"
}
