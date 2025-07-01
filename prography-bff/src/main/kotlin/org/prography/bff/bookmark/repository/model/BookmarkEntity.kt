package org.prography.bff.bookmark.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID

/**
 * 북마크 그룹에 들어가는 가게
 */
@Entity
@Table(name = "BOOKMARK")
class BookmarkEntity(
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
    var id: UUID = UUID.randomUUID(),
    /**
     *
     */
    @Column(name = "user_id", nullable = false)
    var userId: UUID,
    /**
     * 저장된 가게에 속해있는 그룹의 PK
     */
    @Column(name = "group_id", nullable = false)
    val groupId: UUID,
    /**
     * 저장된 가게의 PK
     */
    @Column(name = "place_id", nullable = false, updatable = false)
    val placeId: String,
    /**
     * 저장한 날짜
     */
    @CreatedDate
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
