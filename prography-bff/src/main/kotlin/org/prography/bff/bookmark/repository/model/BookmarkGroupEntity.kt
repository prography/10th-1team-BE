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
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID

/**
 * 북마크 그룹
 */
@Entity
@Table(name = "BOOKMARK_GROUP")
@EntityListeners(AuditingEntityListener::class)
class BookmarkGroupEntity protected constructor() {
    /**
     * 유일성 위한 UUID PK
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
     * 아이콘 모양
     */
    @Column(name = "icon", nullable = false)
    lateinit var icon: String

    /**
     * 해당 그룹의 소유 유저아이디
     */
    @Column(name = "user_id", nullable = false, updatable = false)
    lateinit var userId: UUID

    /**
     * 그룹에 대한 이름
     */
    @Column(name = "group_name", nullable = false)
    lateinit var groupName: String

    /**
     * 그룹에 저장된 가게의 수
     */
    @Column(name = "total")
    val total: Long = 0L

    /**
     * 그룹이 생성된 날짜
     */
    @CreatedDate
    @Column(name = "created_at")
    lateinit var createdAt: LocalDateTime

    /**
     * 그룹이 수정된 날짜
     */
    @LastModifiedDate
    @Column(name = "modified_at")
    lateinit var modifiedAt: LocalDateTime

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BookmarkGroupEntity) return false
        return userId == other.userId && groupName == other.groupName
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + groupName.hashCode()
        return result
    }

    override fun toString(): String = "BookmarkGroupEntity(userId=$userId, groupName=$groupName)"

    constructor(
        icon: String,
        userId: UUID,
        groupName: String,
    ) : this() {
        require(icon.isNotBlank()) { "아이콘은 공백이 될 수 없습니다." }
        require(true) { "userId는 반드시 필요합니다." }
        require(groupName.isNotBlank() && groupName.length <= 20) {
            "그룹 이름은 공백이 아니어야 하며, 1~20자여야 합니다."
        }

        this.icon = icon
        this.userId = userId
        this.groupName = groupName
    }
}
