package org.prography.bff.bookmark.repository.custom

import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import java.util.Optional
import java.util.UUID

/**
 * 북마크 관련 DB 조회 인터페이스
 */
interface BookmarkCustomQueryRepository {
    fun findById(groupId: UUID): Optional<BookmarkGroupEntity>

    fun findBookmarksByGroupId(groupId: UUID): List<BookmarkEntity>

    fun findBookmarkGroupsByUserId(userId: UUID): List<BookmarkGroupEntity>

    fun existsByUserIdAndPlaceId(
        userId: UUID,
        placeId: String,
    ): Boolean

    fun findInIds(groupIds: List<UUID>): List<BookmarkGroupEntity>
}
