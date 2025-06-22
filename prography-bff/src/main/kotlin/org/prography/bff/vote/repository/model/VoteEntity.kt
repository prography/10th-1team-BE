package org.prography.bff.vote.repository.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.prography.bff.vote.repository.model.enumeration.VoteCategory

/**
 * 플랫폼 투표 엔티티
 */
@Entity
@Table(name = "VOTE")
class VoteEntity(
    /**
     * 상호명 + 플랫폼 으로 구성된 복합키
     */
    @EmbeddedId
    val id: VoteId,
    /**
     * 득효 총 합산
     */
    @Column(name = "TOTAL", nullable = false)
    var total: Long,
    /**
     * 이유 - 리뷰가 많음
     */
    @Column(name = "MANY_REVIEWS", nullable = false)
    var manyReview: Long,
    /**
     * 이유 - 리뷰가 자세함
     */
    @Column(name = "DETAILED", nullable = false)
    var detailed: Long,
    /**
     * 이유 - 리뷰가 솔직함
     */
    @Column(name = "HONEST", nullable = false)
    var honest: Long,
    /**
     * 이유 - 리뷰가 정확함
     */
    @Column(name = "ACCURATE", nullable = false)
    var accurate: Long,
) {
    /**
     * 카테고리에 맞춰서 득표 증가
     */
    fun increase(categories: List<VoteCategory>) {
        categories.forEach { category ->
            when (category) {
                VoteCategory.MANY_REVIEWS -> this.manyReview++
                VoteCategory.DETAILED -> this.detailed++
                VoteCategory.HONEST -> this.honest++
                VoteCategory.ACCURATE -> this.accurate++
            }
        }

        this.total += 1
    }

    /**
     * 카테고리별 득표 필드에 대한 반환
     */
    fun toCategoryCountMap(): Map<VoteCategory, Long> {
        return mapOf(
            VoteCategory.MANY_REVIEWS to this.manyReview,
            VoteCategory.DETAILED to this.detailed,
            VoteCategory.HONEST to this.honest,
            VoteCategory.ACCURATE to this.accurate,
        )
    }
}
