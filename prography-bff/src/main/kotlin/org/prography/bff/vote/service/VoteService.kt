package org.prography.bff.vote.service

import jakarta.transaction.Transactional
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.restaurant.RawRestaurantDataRepository
import org.prography.bff.vote.repository.VoteCustomCmdRepositoryImpl
import org.prography.bff.vote.repository.VoteCustomQueryRepositoryImpl
import org.prography.bff.vote.repository.model.VoteEntity
import org.prography.bff.vote.repository.model.VoteHistoryEntity
import org.prography.bff.vote.repository.model.VoteId
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.prography.bff.vote.service.model.VoteCategoryInfo
import org.prography.bff.vote.service.model.VoteInfo
import org.prography.bff.vote.service.model.VoteSubmit
import org.prography.bff.vote.service.model.VoteSummary
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * 투표 관련한 서비스
 */
@Service
class VoteService(
    private val cmdRepository: VoteCustomCmdRepositoryImpl,
    private val queryRepository: VoteCustomQueryRepositoryImpl,
    private val restaurantDataRepository: RawRestaurantDataRepository,
) {
    /**
     * 투표 하기
     */
    @Transactional
    fun submit(
        placeId: String,
        vo: VoteSubmit,
    ) {
        if (placeId.isBlank()) {
            throw IllegalArgumentException()
        }

        val restaurantData =
            restaurantDataRepository.findById(placeId)
                .orElseThrow { NotFoundException.PlaceNotFoundException() }

        val id = VoteId(placeId, vo.platform)
        val entity: VoteEntity =
            queryRepository.findById(id)
                .orElse(
                    VoteEntity(
                        id = id,
                        total = 0,
                        manyReview = 0L,
                        detailed = 0L,
                        honest = 0L,
                        accurate = 0L,
                    ),
                )

        val history =
            VoteHistoryEntity(
                userId = vo.userId,
                placeId = placeId,
                placeName = restaurantData.kakaoPlaceData?.placeName ?: "NO_NAME",
                reaons = vo.categories,
                platform = vo.platform,
                category = restaurantData.kakaoPlaceData?.categoryName?.split(" > ")?.last() ?: "UNDEFINED",
            )

        entity.increase(vo.categories)
        cmdRepository.save(entity)
        cmdRepository.save(history)
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

        val entityMap: Map<VotePlatform, VoteEntity> = queryRepository.findByPlaceId(placeId)

        if (entityMap.isEmpty()) {
            return emptyList()
        }

        return entityMap.map { (platform, entity) ->
            VoteInfo(
                platform = platform,
                total = entity.total,
                categories =
                    entity.toCategoryCountMap().map { (category, count) ->
                        VoteCategoryInfo(category, count)
                    },
            )
        }
    }

    fun getVoteSummary(
        userId: UUID?,
        placeId: String,
    ): VoteSummary {
        val entityMap: Map<VotePlatform, VoteEntity> = queryRepository.findByPlaceId(placeId)

        val total = entityMap.values.sumOf { it.total }

        if (userId == null) {
            return VoteSummary(
                total = total,
                isUserVoted = false,
            )
        }
        val isUserVoted = queryRepository.existsUserVote(userId, placeId)
        return VoteSummary(
            total = total,
            isUserVoted = isUserVoted,
        )
    }
}
