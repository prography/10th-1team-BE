package org.prography.bff.bookmark.repository

import org.prography.bff.bookmark.repository.custom.BookmarkCustomCmdRepository
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.springframework.stereotype.Repository

@Repository
class BookmarkCustomCmdRepositoryImpl(
    private val groupRepository: BookmarkGroupEntityRepository,
    private val bookmarkRepository: BookmarkEntityRepository
) : BookmarkCustomCmdRepository {
    override fun save(group: BookmarkGroupEntity) {
        groupRepository.save(group)
    }

    override fun save(bookmark: BookmarkEntity) {
        bookmarkRepository.save(bookmark)
    }
}
