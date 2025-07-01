package org.prography.bff.bookmark.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.prography.bff.bookmark.repository.model.BookmarkEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

/**
 * BookmarkEntity 관련 Spring Data JPA + Kotlin JDSL 인터페이스
 */
interface BookmarkEntityRepository : JpaRepository<BookmarkEntity, UUID>, KotlinJdslJpqlExecutor
