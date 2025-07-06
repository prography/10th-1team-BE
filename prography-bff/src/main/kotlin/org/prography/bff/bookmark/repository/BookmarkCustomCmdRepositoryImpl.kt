package org.prography.bff.bookmark.repository

import org.prography.bff.bookmark.repository.custom.BookmarkCustomCmdRepository
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity
import org.springframework.stereotype.Repository

@Repository
class BookmarkCustomCmdRepositoryImpl(
    private val groupRepository: BookmarkGroupEntityRepository,
    private val bookmarkRepository: BookmarkEntityRepository,
) : BookmarkCustomCmdRepository {
    override fun saveGroup(group: BookmarkGroupEntity): BookmarkGroupEntity {
        return groupRepository.save(group)
    }

    override fun saveGroups(groups: List<BookmarkGroupEntity>) {
        groupRepository.saveAll(groups)
    }

    override fun saveBookmark(bookmark: BookmarkEntity): BookmarkEntity {
        return bookmarkRepository.save(bookmark)
    }

    override fun saveBookmarks(bookmarks: List<BookmarkEntity>) {
        bookmarkRepository.saveAll(bookmarks)
    }
}
