package org.prography.bff.bookmark.repository.custom

import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.prography.bff.bookmark.repository.model.BookmarkGroupEntity

/**
 * 북마크 관련 DB 저장 및 수정 인터페이스
 */
interface BookmarkCustomCmdRepository {
    fun save(group: BookmarkGroupEntity)

    fun save(bookmark: BookmarkEntity)
}
