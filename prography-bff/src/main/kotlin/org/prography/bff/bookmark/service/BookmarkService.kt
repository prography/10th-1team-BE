package org.prography.bff.bookmark.service

import org.prography.bff.bookmark.repository.BookmarkCustomCmdRepositoryImpl
import org.prography.bff.bookmark.repository.BookmarkCustomQueryRepositoryImpl
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.prography.bff.bookmark.service.model.BookmarkPlace
import org.prography.bff.bookmark.service.model.PlaceGroup
import org.prography.bff.bookmark.service.model.PlaceGroupWithSaved
import org.prography.bff.config.exception.badrequest.InvalidRequestException
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.repository.RestaurantCustomRepository
import org.prography.bff.restaurant.repository.model.PlaceInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class BookmarkService(
    private val bookmarkCmdRepository: BookmarkCustomCmdRepositoryImpl,
    private val bookmarkQueryRepository: BookmarkCustomQueryRepositoryImpl,
    private val restaurantDataRepository: RestaurantCustomRepository,
) {
    fun createBookmarkGroup(
        userId: UUID,
        icon: String,
        groupName: String,
    ): UUID {
        if (bookmarkQueryRepository.existsBookmarkGroup(userId = userId, groupName = groupName)) {
            throw InvalidRequestException.AlreadyGroup()
        }
        val groupEntity = BookmarkGroupEntity(userId = userId, icon = icon, groupName = groupName)
        return bookmarkCmdRepository.saveGroup(groupEntity).id
    }

    @Transactional
    fun addBookmarkAtGroup(
        groupIds: List<UUID>,
        placeId: String,
    ) {
        if (!restaurantDataRepository.existsById(placeId)) {
            throw NotFoundException.PlaceNotFoundException()
        }

        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findInIds(groupIds)
        if (groups.isEmpty()) {
            return
        }

        val bookmarks: List<BookmarkEntity> = groups.map { it.addBookmark(placeId = placeId) }
        bookmarkCmdRepository.saveGroups(groups)
        bookmarkCmdRepository.saveBookmarks(bookmarks)
    }

    @Transactional
    fun removeBookmarkAtGroup(
        userId: UUID,
        placeId: String,
        groupIds: List<UUID>,
    ) {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findInIds(groupIds = groupIds)
        if (groups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> =
            bookmarkQueryRepository.findMatchedBookmarksInGroup(
                groupIds =
                    groups.map {
                        it.id
                    },
                placeId = placeId,
            )

        groups.forEach {
            it.removeBookmark(bookmarks)
        }

        bookmarkCmdRepository.saveGroups(groups)
        bookmarkCmdRepository.deleteBookmarks(bookmarks)
    }

    @Transactional
    fun removeBookmarkAtGroup(
        userId: UUID,
        groupId: UUID,
        placeIds: List<String>,
    ) {
        val group: BookmarkGroupEntity =
            bookmarkQueryRepository.findById(groupId = groupId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        val bookmarks: List<BookmarkEntity> = bookmarkQueryRepository.findMatchedBookmarksInGroup(groupId = group.id, placeIds = placeIds)

        group.removeBookmark(bookmarks)
        bookmarkCmdRepository.deleteBookmarks(bookmarks)
    }

    fun replaceBookmarkAtGroup(
        source: UUID,
        target: UUID,
        placeIds: List<String>,
    ) {
    }

    @Transactional
    fun deleteBookmarkGroup(
        userId: UUID,
        groupId: UUID,
    ) {
        val group: BookmarkGroupEntity =
            bookmarkQueryRepository.findById(groupId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        if (group.userId != userId) {
            throw InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> = bookmarkQueryRepository.findBookmarksByGroupId(group.id)

        bookmarkCmdRepository.deleteBookmarks(bookmarks)
        bookmarkCmdRepository.deleteGroup(group)
    }

    fun isBookmark(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkQueryRepository.existsBookmark(
            userId = userId,
            placeId = placeId,
        )
    }

    fun getBookmarkGroups(userId: UUID): List<PlaceGroup> {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findBookmarkGroupsByUserId(userId)

        return groups.map {
            PlaceGroup(id = it.id, name = it.groupName, icon = it.icon, total = it.total, createdAt = it.createdAt, savedAt = it.modifiedAt)
        }
    }

    @Transactional(readOnly = true)
    fun getBookmarkGroups(
        userId: UUID,
        placeId: String,
    ): PlaceGroupWithSaved {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findBookmarkGroupsByUserId(userId)
        val savedGroupIds: Set<UUID> =
            bookmarkQueryRepository.findMatchedBookmarksInGroup(groupIds = groups.map { it.id }, placeId = placeId)
                .mapTo(mutableSetOf()) { it.groupId }

        return PlaceGroupWithSaved(
            placeGroups =
                groups.map {
                    PlaceGroup(
                        id = it.id,
                        name = it.groupName,
                        icon = it.icon,
                        total = it.total,
                        createdAt = it.createdAt,
                        savedAt = it.modifiedAt,
                    )
                },
            savedGroupIds = savedGroupIds.toList(),
        )
    }

    @Transactional(readOnly = true)
    fun getBookmarks(groupId: UUID): List<BookmarkPlace> {
        val bookmarks: List<BookmarkEntity> =
            bookmarkQueryRepository.findBookmarksByGroupId(groupId)

        if (bookmarks.isEmpty()) {
            return emptyList()
        }

        val placeInfos: Map<String, PlaceInfo> =
            restaurantDataRepository
                .findKakaoPlaceInfoInIds(bookmarks.map { it.placeId })
                .associateBy { it.id }

        return bookmarks.map { bookmark ->
            val info = placeInfos[bookmark.placeId]
            BookmarkPlace(
                id = bookmark.id,
                placeId = bookmark.placeId,
                placeName = info?.placeName ?: "NO_NAME",
                roadAddress = info?.roadAddress ?: "",
                category = info?.categoryName ?: "UNDEFINED",
                legal = info?.leaglCode ?: 0,
                savedAt = bookmark.savedAt,
            )
        }
    }
}
