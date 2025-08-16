package org.prography.bff.bookmark.repository.custom

import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import java.util.Optional
import java.util.UUID

/**
 * 북마크 관련 DB 조회 인터페이스
 */
interface BookmarkCustomQueryRepository {
    fun findGroupById(groupId: UUID): Optional<BookmarkGroupEntity>

    fun findBookmarksByGroupId(groupId: UUID): List<BookmarkEntity>

    fun findGroupsByUserId(userId: UUID): List<BookmarkGroupEntity>

    fun existsBookmark(
        userId: UUID,
        placeId: String,
    ): Boolean

    fun findGroupsInIds(groupIds: List<UUID>): List<BookmarkGroupEntity>

    fun existsGroup(
        userId: UUID,
        groupName: String,
    ): Boolean

    fun findMatchedBookmarksInGroup(
        groupIds: List<UUID>,
        placeId: String,
    ): List<BookmarkEntity>

    fun findMatchedBookmarksInGroup(
        groupId: UUID,
        placeIds: List<String>,
    ): List<BookmarkEntity>

    fun findMatchedBookmarksInGroups(
        groupIds: List<UUID>,
        placeIds: List<String>,
    ): List<BookmarkEntity>

    fun getNumberOfBookmark(groupId: UUID): Long

    fun getNumberOfBookmark(groupIds: List<UUID>): Map<UUID, Long>

    fun findRouletteGroupsByUserId(userId: UUID): List<BookmarkGroupEntity>

    fun existsRoulette(
        userId: UUID,
        placeId: String,
    ): Boolean
}
