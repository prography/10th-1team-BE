package org.prography.bff.vote.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteId
import org.springframework.data.jpa.repository.JpaRepository

/**
 * VoteEntity 관련 Spring Data JPA + Kotlin JDSL 인터페이스
 */
interface VoteEntityRepository : JpaRepository<VoteEntity, VoteId>, KotlinJdslJpqlExecutor
