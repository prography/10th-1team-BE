package org.prography.bff.bookmark.repository

import org.prography.bff.bookmark.repository.custom.BookmarkCustomQueryRepository
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class BookmarkCustomQueryRepositoryImpl(
    private val groupRepository: BookmarkGroupEntityRepository,
    private val bookmarkRepository: BookmarkEntityRepository,
) : BookmarkCustomQueryRepository {
    override fun findById(groupId: UUID): Optional<BookmarkGroupEntity> {
        return groupRepository.findById(groupId)
    }

    override fun findBookmarksByGroupId(groupId: UUID): List<BookmarkEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkEntity::class))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::groupId).eq(groupId),
                )
        }.filterNotNull()
    }

    override fun findBookmarkGroupsByUserId(userId: UUID): List<BookmarkGroupEntity> {
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
                        path(BookmarkEntity::placeId).eq(placeId),
                    ),
                )
        }.isNotEmpty()
    }

    override fun findInIds(groupIds: List<UUID>): List<BookmarkGroupEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkGroupEntity::class))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::id).`in`(groupIds),
                )
        }.filterNotNull()
    }

    override fun existsBookmarkGroup(
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
}
