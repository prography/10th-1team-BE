package org.prography.bff.bookmark.service

import org.prography.bff.bookmark.repository.BookmarkCustomCmdRepositoryImpl
import org.prography.bff.bookmark.repository.BookmarkCustomQueryRepositoryImpl
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.prography.bff.bookmark.service.model.BookmarkPlace
import org.prography.bff.bookmark.service.model.PlaceGroup
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.repository.RestaurantCustomRepository
import org.prography.bff.restaurant.repository.model.PlaceInfo
import java.util.UUID

class BookmarkService(
    private val bookmarkCmdRepository: BookmarkCustomCmdRepositoryImpl,
    private val bookmarkQueryRepository: BookmarkCustomQueryRepositoryImpl,
    private val restaurantDataRepository: RestaurantCustomRepository,
) {
    fun createBookmarkGroup(
        userId: UUID,
        icon: String,
        groupName: String,
    ) {
        val groupEntity = BookmarkGroupEntity(userId = userId, icon = icon, groupName = groupName)
        bookmarkCmdRepository.save(groupEntity)
    }

    fun addBookmarkAtGroup(
        groupId: UUID,
        placeId: String,
    ) {
        val bookmarkGroup: BookmarkGroupEntity =
            bookmarkQueryRepository.findById(groupId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        val bookmark = bookmarkGroup.addBookmark(placeId = placeId)
        bookmarkCmdRepository.save(bookmark)
        bookmarkCmdRepository.save(bookmarkGroup)
    }

    fun removeBookmarkAtGroup(
        userId: UUID,
        groupIds: List<UUID>,
        placeId: String,
    ) {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findInIds(groupIds = groupIds)
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

    fun isBookmark(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkQueryRepository.existsByUserIdAndPlaceId(
            userId = userId,
            placeId = placeId,
        )
    }

    fun getBookmarkGroups(userId: UUID): List<PlaceGroup> {
        val groupEntities: List<BookmarkGroupEntity> = bookmarkQueryRepository.findBookmarkGroupsByUserId(userId)

        return groupEntities.map {
            PlaceGroup(id = it.id, name = it.groupName, icon = it.icon, total = it.total, createdAt = it.createdAt, savedAt = it.modifiedAt)
        }
    }

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
