package org.prography.bff.bookmark.repository

import org.prography.bff.bookmark.repository.custom.BookmarkCustomQueryRepository
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.prography.bff.bookmark.repository.model.BookmarkId
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class BookmarkCustomQueryRepositoryImpl(
    private val groupRepository: BookmarkGroupEntityRepository,
    private val bookmarkRepository: BookmarkEntityRepository,
) : BookmarkCustomQueryRepository {
    override fun findGroupById(groupId: UUID): Optional<BookmarkGroupEntity> {
        return groupRepository.findById(groupId)
    }

    override fun findBookmarksByGroupId(groupId: UUID): List<BookmarkEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkEntity::class))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::id)(BookmarkId::groupId).eq(groupId),
                )
        }.filterNotNull()
    }

    override fun findGroupsByUserId(userId: UUID): List<BookmarkGroupEntity> {
        return groupRepository.findAll {
            select(entity(BookmarkGroupEntity::class))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::userId).eq(userId),
                )
        }.filterNotNull()
    }

    override fun existsBookmark(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkRepository.findAll(limit = 1) {
            select(intLiteral(1))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::userId).eq(userId).and(
                        path(BookmarkEntity::id)(BookmarkId::placeId).eq(placeId),
                    ),
                )
        }.isNotEmpty()
    }

    override fun findGroupsInIds(groupIds: List<UUID>): List<BookmarkGroupEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkGroupEntity::class))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::id).`in`(groupIds),
                )
        }.filterNotNull()
    }

    override fun existsGroup(
        userId: UUID,
        groupName: String,
    ): Boolean {
        return bookmarkRepository.findAll(limit = 1) {
            select(intLiteral(1))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::userId).eq(userId).and(
                        path(BookmarkGroupEntity::groupName).eq(groupName),
                    ),
                )
        }.isNotEmpty()
    }

    override fun findMatchedBookmarksInGroup(
        groupIds: List<UUID>,
        placeId: String,
    ): List<BookmarkEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkEntity::class))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::id)(BookmarkId::groupId).`in`(groupIds).and(
                        path(BookmarkEntity::id)(BookmarkId::placeId).eq(placeId),
                    ),
                )
        }.filterNotNull()
    }

    override fun findMatchedBookmarksInGroup(
        groupId: UUID,
        placeIds: List<String>,
    ): List<BookmarkEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkEntity::class))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::id)(BookmarkId::groupId).eq(groupId).and(
                        path(BookmarkEntity::id)(BookmarkId::placeId).`in`(placeIds),
                    ),
                )
        }.filterNotNull()
    }
}
