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
    var total: Long = 0L

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

    @Column(name = "roulette", nullable = false)
    var roulette: Boolean = false

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BookmarkGroupEntity) return false
        return userId == other.userId && groupName == other.groupName && roulette == other.roulette
    }

    override fun hashCode(): Int {
        var result = userId.hashCode()
        result = 31 * result + groupName.hashCode()
        result = 31 * result + roulette.hashCode()
        return result
    }

    override fun toString(): String = "BookmarkGroupEntity(userId=$userId, groupName=$groupName)"

    constructor(
        icon: String,
        userId: UUID,
        groupName: String,
        roulette: Boolean = false,
    ) : this() {
        require(icon.isNotBlank()) { "아이콘은 공백이 될 수 없습니다." }
        require(groupName.isNotBlank() && groupName.length <= 20) {
            "그룹 이름은 공백이 아니어야 하며, 1~20자여야 합니다."
        }

        this.icon = icon
        this.userId = userId
        this.groupName = groupName
        this.roulette = roulette
    }

    fun addBookmark(placeId: String): BookmarkEntity {
        val limit = if (roulette) 8 else 1000
        val message =
            if (roulette) {
                "룰렛 그룹에는 최대 8개의 가게만 추가 할 수 있습니다. (current=$total)"
            } else {
                "이 그룹에는 더 이상 가게를 추가할 수 없습니다. (current=$total)"
            }
        require(total < limit) { message }

        return BookmarkEntity.of(this, placeId).also {
            total += 1
        }
    }

    fun addBookmarks(placeIds: List<String>): List<BookmarkEntity> {
        val limit = if (roulette) 8 else 1000
        val message =
            if (roulette) {
                "룰렛 그룹에는 최대 8개의 가게만 추가할 수 있습니다. 추가하려는 개수: ${placeIds.size}, 현재 개수: $total"
            } else {
                "이 그룹에는 더 이상 가게를 추가할 수 없습니다. 추가하려는 개수: ${placeIds.size}, 현재 개수: $total"
            }
        require(placeIds.size + total <= limit) { message }

        return placeIds.map {
            BookmarkEntity.of(this, placeId = it)
        }.also {
            total += placeIds.size
        }
    }

    fun removeBookmark(placeId: String): BookmarkEntity {
        require(total != 0L) {
            "이 그룹에는 저장된 가게가 존재하지 없습니다. (current=$total)"
        }
        return BookmarkEntity.of(this, placeId).also {
            total -= 1
        }
    }

    fun removeBookmarks(placeIds: List<String>): List<BookmarkEntity> {
        require(total != 0L) {
            "이 그룹에는 저장된 가게가 존재하지 없습니다. (current=$total)"
        }

        require(placeIds.size <= total) {
            "삭제하려는 가게 수(${placeIds.size})가 현재 그룹에 저장된 개수($total)보다 많습니다."
        }

        return placeIds.map {
            BookmarkEntity.of(this, placeId = it)
        }.also {
            total -= placeIds.size
        }
    }

    fun changeName(groupName: String) {
        require(groupName.isNotBlank() && groupName.length <= 20) {
            "그룹 이름은 공백이 아니어야 하며, 1~20자여야 합니다."
        }
        this.groupName = groupName
    }

    fun changeIcon(icon: String) {
        require(icon.isNotBlank()) { "아이콘은 공백이 될 수 없습니다." }
        this.icon = icon
    }
}
