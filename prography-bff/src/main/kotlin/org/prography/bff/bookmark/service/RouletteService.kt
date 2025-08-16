package org.prography.bff.bookmark.service

import org.prography.bff.bookmark.repository.BookmarkCustomCmdRepositoryImpl
import org.prography.bff.bookmark.repository.BookmarkCustomQueryRepositoryImpl
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.prography.bff.bookmark.service.model.PlaceGroup
import org.prography.bff.bookmark.service.model.PlaceGroupWithSaved
import org.prography.bff.config.exception.badrequest.InvalidRequestException
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.repository.RestaurantCustomRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RouletteService(
    private val bookmarkCmdRepository: BookmarkCustomCmdRepositoryImpl,
    private val bookmarkQueryRepository: BookmarkCustomQueryRepositoryImpl,
    private val restaurantDataRepository: RestaurantCustomRepository,
) {
    fun createRouletteGroup(
        userId: UUID,
        icon: String,
        rouletteName: String,
    ): UUID {
        if (bookmarkQueryRepository.existsGroup(userId = userId, groupName = rouletteName)) {
            throw InvalidRequestException.AlreadyGroup()
        }
        val groupEntity = BookmarkGroupEntity(userId = userId, icon = icon, groupName = rouletteName, roulette = true)
        return bookmarkCmdRepository.saveGroup(groupEntity).id
    }

    fun addPlaceAtRoulette(
        userId: UUID,
        rouletteIds: List<UUID>,
        placeId: String,
    ) {
        if (!restaurantDataRepository.existsById(placeId)) {
            throw NotFoundException.PlaceNotFoundException()
        }

        val rouletteGroups: List<BookmarkGroupEntity> =
            bookmarkQueryRepository
                .findGroupsInIds(rouletteIds)

        if (rouletteGroups.isEmpty()) {
            return
        }

        if (rouletteGroups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val rouletteItems: List<BookmarkEntity> =
            rouletteGroups.map {
                it.addBookmark(placeId = placeId)
            }

        try {
            bookmarkCmdRepository.saveGroups(rouletteGroups)
            bookmarkCmdRepository.saveBookmarks(rouletteItems)
        } catch (_: DataIntegrityViolationException) {
            throw InvalidRequestException.AlreadyBookmark()
        }
    }

    fun getRouletteGroups(userId: UUID): List<PlaceGroup> {
        val rouletteGroups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findRouletteGroupsByUserId(userId)

        return rouletteGroups.map {
            PlaceGroup(id = it.id, name = it.groupName, icon = it.icon, total = it.total, createdAt = it.createdAt, savedAt = it.modifiedAt)
        }
    }

    @Transactional(readOnly = true)
    fun getRouletteGroups(
        userId: UUID,
        placeId: String,
    ): PlaceGroupWithSaved {
        val rouletteGroups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findGroupsByUserId(userId)

        if (rouletteGroups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val savedGroupIds: List<UUID> =
            bookmarkQueryRepository.findMatchedBookmarksInGroup(groupIds = rouletteGroups.map { it.id }, placeId = placeId)
                .map { it.id.groupId }
                .distinct()

        return PlaceGroupWithSaved(
            placeGroups =
                rouletteGroups.map {
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

    fun updateRoulette(
        userId: UUID,
        rouletteId: UUID,
        name: String,
        icon: String,
    ) {
        val roulette: BookmarkGroupEntity =
            bookmarkQueryRepository.findGroupById(groupId = rouletteId)
                .orElseThrow { NotFoundException.GroupNotFound() }

        if (roulette.userId != userId) {
            throw InvalidRequestException.MismatchUser()
        }

        roulette.changeName(groupName = name)
        roulette.changeIcon(icon = icon)
        bookmarkCmdRepository.saveGroup(group = roulette)
    }

    @Transactional
    fun updateItemAtRoulette(
        userId: UUID,
        placeId: String,
        desiredRouletteIds: Set<UUID>,
    ) {
        if (!restaurantDataRepository.existsById(placeId)) {
            throw NotFoundException.PlaceNotFoundException()
        }

        val userGroups: List<BookmarkGroupEntity> = bookmarkQueryRepository.findRouletteGroupsByUserId(userId = userId)

        if (userGroups.isEmpty()) {
            throw NotFoundException.GroupNotFound()
        }

        if (userGroups.any { it.userId != userId }) {
            throw InvalidRequestException.MismatchUser()
        }

        val userGroupsById: Map<UUID, BookmarkGroupEntity> = userGroups.associateBy { it.id }
        val existingBookmarkedGroupIds: Set<UUID> =
            bookmarkQueryRepository.findMatchedBookmarksInGroup(groupIds = userGroups.map { it.id }, placeId = placeId)
                .map { it.id.groupId }
                .toSet()

        val groupIdsToAdd = desiredRouletteIds - existingBookmarkedGroupIds
        if (groupIdsToAdd.isNotEmpty()) {
            val bookmarksToAdd: List<BookmarkEntity> =
                groupIdsToAdd.mapNotNull { groupId ->
                    userGroupsById[groupId]?.addBookmark(placeId = placeId)
                }
            bookmarkCmdRepository.saveBookmarks(bookmarksToAdd)
        }

        val groupIdsToRemove = existingBookmarkedGroupIds - desiredRouletteIds
        if (groupIdsToRemove.isNotEmpty()) {
            val bookmarkIdsToRemove: List<BookmarkEntity> =
                groupIdsToRemove.mapNotNull { groupId ->
                    userGroupsById[groupId]?.removeBookmark(placeId = placeId)
                }
            bookmarkCmdRepository.deleteBookmarks(bookmarkIdsToRemove)
        }

        bookmarkCmdRepository.saveGroups(userGroups)
    }

    fun isAdded(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkQueryRepository.existsRoulette(userId = userId, placeId = placeId)
    }
}
