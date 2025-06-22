package org.prography.bff.vote.service

import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.service.model.VoteCategoryInfo
import org.prography.bff.vote.service.model.VoteInfo
import org.prography.bff.vote.service.model.VoteSubmit

/**
 * 투표 관련한 서비스
 */
class VoteService {
    private var voteMap: MutableMap<VoteId, VoteEntity> = mutableMapOf()

    /**
     * 투표 하기
     */
    fun submit(
        placeId: String,
        vo: VoteSubmit,
    ) {
        if (placeId.isBlank()) {
            throw IllegalArgumentException()
        }

        val id = VoteId(placeId, vo.platform)
        val entity =
            voteMap.getOrDefault(
                id,
                VoteEntity(
                    id = id,
                    total = 0,
                    manyReview = 0L,
                    detailed = 0L,
                    honest = 0L,
                    accurate = 0L,
                ),
            )

        entity.increase(vo.reason)
        voteMap[id] = entity
    }

    /**
     * 투표 내용 수정
     */
    fun modify(placeId: String) {
        // TODO 아직 기획 미정
    }

    /**
     * 투표 취소
     */
    fun cancel(placeId: String) {
        // TODO 아직 기획 미정
    }

    /**
     * 투표 결과 반환
     */
    fun getVoteResult(placeId: String): List<VoteInfo> {
        if (placeId.isBlank()) {
            throw IllegalArgumentException()
        }

        val entities =
            voteMap
                .filterKeys { it.id == placeId }
                .values

        return entities.map { entity: VoteEntity ->
            VoteInfo(
                platform = entity.id.platform,
                total = entity.total,
                categories =
                    entity.toCategoryCountMap().map { category ->
                        VoteCategoryInfo(category.key, category.value)
                    },
            )
        }
    }

    /**
     * 사용자 투표 이력
     */
    fun getSubmitHistory(userId: String) {
    }
}
