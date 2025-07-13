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
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * 가게 저장 관련한 서비스
 */
@Service
class BookmarkService(
    private val bookmarkCmdRepository: BookmarkCustomCmdRepositoryImpl,
    private val bookmarkQueryRepository: BookmarkCustomQueryRepositoryImpl,
    private val restaurantDataRepository: RestaurantCustomRepository,
) {
    /**
     * 저장 그룹생성
     */
    fun createBookmarkGroup(
        userId: UUID,
        icon: String,
        groupName: String,
    ): UUID {
        if (bookmarkQueryRepository.existsGroup(userId = userId, groupName = groupName)) {
            throw InvalidRequestException.AlreadyGroup()
        }
        val groupEntity = BookmarkGroupEntity(userId = userId, icon = icon, groupName = groupName)
        return bookmarkCmdRepository.saveGroup(groupEntity).id
    }

    /**
     * 저장 그룹에 가게 추가
     */
    @Transactional
    fun addBookmarkAtGroup(
        userId: UUID,
        groupIds: List<UUID>,
        placeId: String,
    ) {
        if (!restaurantDataRepository.existsById(placeId)) {
            throw NotFoundException.PlaceNotFoundException()
        }

        val groups: List<BookmarkGroupEntity> =
            bookmarkQueryRepository.findGroupsInIds(groupIds)

        if (groups.isEmpty()) {
            return
        }

        if (groups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> =
            groups.map {
                it.addBookmark(placeId = placeId)
            }

        try {
            bookmarkCmdRepository.saveGroups(groups)
            bookmarkCmdRepository.saveBookmarks(bookmarks)
        } catch (e: DataIntegrityViolationException) {
            throw InvalidRequestException.AlreadyBookmark()
        }
    }

    /**
     * 그룹에 저장된 가게 삭제
     */
    @Transactional
    fun removeBookmarkAtGroup(
        userId: UUID,
        placeId: String,
        groupIds: List<UUID>,
    ) {
        val groups: List<BookmarkGroupEntity> =
            bookmarkQueryRepository.findGroupsInIds(groupIds = groupIds)

        if (groups.isEmpty()) {
            return
        }
        if (groups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> =
            groups.map {
                it.removeBookmark(placeId)
            }

        bookmarkCmdRepository.saveGroups(groups)
        bookmarkCmdRepository.deleteBookmarks(bookmarks)
    }

    /**
     * 그룹에 저장된 가게 삭제
     */
    @Transactional
    fun removeBookmarkAtGroup(
        userId: UUID,
        groupId: UUID,
        placeIds: List<String>,
    ) {
        val group: BookmarkGroupEntity =
            bookmarkQueryRepository.findGroupById(groupId = groupId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        if (group.userId != userId) {
            InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> =
            group.removeBookmarks(placeIds = placeIds)

        bookmarkCmdRepository.saveGroup(group)
        bookmarkCmdRepository.deleteBookmarks(bookmarks)
    }

    /**
     * 그룹의 가게를 다른 그룹으로 디동
     */
    @Transactional
    fun moveBookmarkAtGroup(
        userId: UUID,
        source: UUID,
        targets: List<UUID>,
        placeIds: List<String>,
    ) {
        val groups: List<BookmarkGroupEntity> =
            bookmarkQueryRepository.findGroupsByUserId(userId = userId)

        if (groups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val groupMap: Map<UUID, BookmarkGroupEntity> = groups.associateBy { it.id }

        val sourceGroup: BookmarkGroupEntity =
            groupMap[source]
                ?: throw NotFoundException.GroupNotFound()

        val targetGroups: List<BookmarkGroupEntity> =
            targets.map {
                groupMap[it] ?: throw NotFoundException.GroupNotFound()
            }

        val sourceBookmarks: List<BookmarkEntity> = sourceGroup.removeBookmarks(placeIds)
        bookmarkCmdRepository.deleteBookmarks(sourceBookmarks)

        val targetBookmarks: List<BookmarkEntity> =
            targetGroups.flatMap {
                it.addBookmarks(placeIds = placeIds)
            }
        bookmarkCmdRepository.saveBookmarks(targetBookmarks)

        val modifiedGroups = listOf(sourceGroup) + targetGroups
        bookmarkCmdRepository.saveGroups(modifiedGroups)
    }

    /**
     * 저장 그룹을 삭제 합니다.
     */
    @Transactional
    fun deleteBookmarkGroup(
        userId: UUID,
        groupId: UUID,
    ) {
        val group: BookmarkGroupEntity =
            bookmarkQueryRepository.findGroupById(groupId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        if (group.userId != userId) {
            throw InvalidRequestException.MismatchUser()
        }

        val bookmarks: List<BookmarkEntity> = bookmarkQueryRepository.findBookmarksByGroupId(group.id)

        bookmarkCmdRepository.deleteBookmarks(bookmarks)
        bookmarkCmdRepository.deleteGroup(group)
    }

    /**
     * 해당 가게가 유저가 가지고 있는 그룹에 저장되어 있는지 확인
     */
    fun isBookmark(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkQueryRepository.existsBookmark(
            userId = userId,
            placeId = placeId,
        )
    }

    /**
     * 유저가 소유하고 있는 저장 그룹 조회
     */
    fun getBookmarkGroups(userId: UUID): List<PlaceGroup> {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findGroupsByUserId(userId)

        return groups.map {
            PlaceGroup(id = it.id, name = it.groupName, icon = it.icon, total = it.total, createdAt = it.createdAt, savedAt = it.modifiedAt)
        }
    }

    /**
     * 유저가 소유하고 있는 저장 그룹을 저장 유무와 함께 조회
     */
    @Transactional(readOnly = true)
    fun getBookmarkGroups(
        userId: UUID,
        placeId: String,
    ): PlaceGroupWithSaved {
        val groups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findGroupsByUserId(userId)

        if (groups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val savedGroupIds: List<UUID> =
            bookmarkQueryRepository.findMatchedBookmarksInGroup(groupIds = groups.map { it.id }, placeId = placeId)
                .map { it.id.groupId }
                .distinct()

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

    /**
     *
     */
    @Transactional(readOnly = true)
    fun getBookmarks(groupId: UUID): List<BookmarkPlace> {
        val bookmarks: List<BookmarkEntity> =
            bookmarkQueryRepository.findBookmarksByGroupId(groupId)

        if (bookmarks.isEmpty()) {
            return emptyList()
        }

        val placeInfos: Map<String, PlaceInfo> =
            restaurantDataRepository
                .findKakaoPlaceInfoInIds(bookmarks.map { it.id.placeId })
                .associateBy { it.id }

        return bookmarks.map { bookmark ->
            val info = placeInfos[bookmark.id.placeId]
            BookmarkPlace(
                groupId = bookmark.id.groupId,
                placeId = bookmark.id.placeId,
                placeName = info?.placeName ?: "NO_NAME",
                roadAddress = info?.roadAddress ?: "",
                category = info?.categoryName ?: "UNDEFINED",
                legal = info?.leaglCode ?: 0,
                savedAt = bookmark.savedAt,
            )
        }
    }
}
