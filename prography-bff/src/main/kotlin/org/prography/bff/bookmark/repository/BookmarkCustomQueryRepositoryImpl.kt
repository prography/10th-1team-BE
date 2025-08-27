package org.prography.bff.bookmark.repository

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions.expression
import jakarta.persistence.Tuple
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
                    path(BookmarkGroupEntity::userId).eq(userId)
                        .and(path(BookmarkGroupEntity::roulette).eq(false)),
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
                    path(BookmarkGroupEntity::roulette).eq(false).and(
                        path(BookmarkGroupEntity::userId).eq(userId).and(
                            path(BookmarkGroupEntity::groupName).eq(groupName),
                        ),
                    ),
                )
        }.isNotEmpty()
    }

    override fun existsRouletteGroup(
        userId: UUID,
        groupName: String,
    ): Boolean {
        return bookmarkRepository.findAll(limit = 1) {
            select(intLiteral(1))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::roulette).eq(true).and(
                        path(BookmarkGroupEntity::userId).eq(userId).and(
                            path(BookmarkGroupEntity::groupName).eq(groupName),
                        ),
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

    override fun findMatchedBookmarksInGroups(
        groupIds: List<UUID>,
        placeIds: List<String>,
    ): List<BookmarkEntity> {
        return bookmarkRepository.findAll {
            select(entity(BookmarkEntity::class))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::id)(BookmarkId::groupId).`in`(groupIds).and(
                        path(BookmarkEntity::id)(BookmarkId::placeId).`in`(placeIds),
                    ),
                )
        }.filterNotNull()
    }

    override fun getNumberOfBookmark(groupId: UUID): Long {
        return bookmarkRepository.findAll {
            select(count(path(BookmarkEntity::id)(BookmarkId::groupId)))
                .from(entity(BookmarkEntity::class))
                .where(
                    path(BookmarkEntity::id)(BookmarkId::groupId).eq(groupId),
                )
        }.first() ?: 0L
    }

    override fun getNumberOfBookmark(groupIds: List<UUID>): Map<UUID, Long> {
        if (groupIds.isEmpty()) {
            return emptyMap()
        }

        val groupIdAlias: Expression<UUID> = expression(UUID::class, "groupId")
        val countAlias: Expression<Long> = expression(Long::class, "count")

        val tuples: List<Tuple> =
            bookmarkRepository.findAll {
                val groupIdPath = path(BookmarkEntity::id)(BookmarkId::groupId)
                select<Tuple>(
                    groupIdPath.`as`(groupIdAlias),
                    count(BookmarkEntity::id).`as`(countAlias),
                )
                    .from(entity(BookmarkEntity::class))
                    .where(
                        groupIdPath.`in`(groupIds),
                    )
                    .groupBy(groupIdPath)
            }.filterNotNull()

        return tuples.associate { tuple ->
            val groupId: UUID = tuple.get("groupId", UUID::class.java)
            val cnt: Long = tuple.get("count", Long::class.java)

            groupId to cnt
        }
    }

    override fun findRouletteGroupsByUserId(userId: UUID): List<BookmarkGroupEntity> {
        return groupRepository.findAll {
            select(entity(BookmarkGroupEntity::class))
                .from(entity(BookmarkGroupEntity::class))
                .where(
                    path(BookmarkGroupEntity::userId).eq(userId)
                        .and(
                            path(BookmarkGroupEntity::roulette).eq(true),
                        ),
                )
        }.filterNotNull()
    }

    override fun existsRoulette(
        userId: UUID,
        placeId: String,
    ): Boolean {
        return bookmarkRepository.findAll(limit = 1) {
            select(intLiteral(1))
                .from(
                    entity(BookmarkEntity::class),
                    join(BookmarkGroupEntity::class)
                        .on(path(BookmarkEntity::id)(BookmarkId::groupId).eq(path(BookmarkGroupEntity::id))),
                )
                .where(
                    and(
                        path(BookmarkEntity::userId).eq(userId),
                        path(BookmarkEntity::id)(BookmarkId::placeId).eq(placeId),
                        path(BookmarkGroupEntity::roulette).eq(true),
                    ),
                )
        }.isNotEmpty()
    }
}
