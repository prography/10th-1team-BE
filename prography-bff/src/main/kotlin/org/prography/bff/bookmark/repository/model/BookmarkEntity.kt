package org.prography.bff.bookmark.repository.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.Objects
import java.util.UUID

/**
 * 북마크 그룹에 들어가는 가게
 */
@Entity
@Table(name = "BOOKMARK")
@EntityListeners(AuditingEntityListener::class)
class BookmarkEntity protected constructor() {
    /**
     * 그룹 아이디 + 가게 아이디로 구성된 복합키
     */
    @EmbeddedId
    lateinit var id: BookmarkId

    /**
     * 해당 가게를 저장한 유저의 PK
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    lateinit var userId: UUID

    /**
     * 저장한 날짜
     */
    @CreatedDate
    @Column(name = "saved_at")
    lateinit var savedAt: LocalDateTime

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BookmarkEntity) return false
        return Objects.equals(id, (other as? BookmarkEntity)?.id)
    }

    override fun hashCode(): Int = Objects.hashCode(id)

    override fun toString(): String = "BookmarkEntity(userId=$userId, groupId=${id.groupId}, placeId=${id.placeId})"

    private constructor(userId: UUID, groupId: UUID, placeId: String) : this() {
        require(placeId.isNotBlank()) { "placeId는 공백일 수 없습니다." }
        this.id = BookmarkId(placeId = placeId, groupId = groupId)
        this.userId = userId
    }

    companion object {
        fun of(
            group: BookmarkGroupEntity,
            placeId: String,
        ): BookmarkEntity = BookmarkEntity(group.userId, group.id, placeId)
    }
}
