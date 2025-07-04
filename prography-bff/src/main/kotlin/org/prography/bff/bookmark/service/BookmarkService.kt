package org.prography.bff.bookmark.service

import org.prography.bff.bookmark.repository.BookmarkCustomCmdRepositoryImpl
import org.prography.bff.bookmark.repository.BookmarkCustomQueryRepositoryImpl
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import java.util.UUID

class BookmarkService(
    private val bookmarkCmdRepository: BookmarkCustomCmdRepositoryImpl,
    private val bookmarkQueryRepository: BookmarkCustomQueryRepositoryImpl
) {
    fun createBookmarkGroup(
        userId: UUID,
        icon: String,
        groupName: String,
    ) {
        val groupEntity = BookmarkGroupEntity(userId = userId, icon = icon, groupName = groupName)
    }

    fun addBookmarkAtGroup(
        userId: UUID,
        groupId: UUID,
        placeId: String,
    ) {
    }

    fun removeBookmarkAtGroup(
        userId: UUID,
        groupId: UUID,
        placeId: String,
    ) {
    }

    fun removeBookmarkAtGroup(
        userId: UUID,
        groupId: UUID,
        placeIds: List<String>,
    ) {
    }

    fun replaceBookmarkAtGroup(
        userId: UUID,
        source: UUID,
        target: UUID,
        placeIds: List<String>,
    ) {
    }

    fun deleteBookmarkGroup(
        userId: UUID,
        groupId: UUID,
    ) {
    }

    fun isBookmark(userId: UUID): Boolean {
        return false
    }

    fun getBookmarkGroups(userId: UUID) {
    }

    fun getBookmarks(groupId: UUID) {
    }
}
