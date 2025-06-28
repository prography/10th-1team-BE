package org.prography.bff.vote.controller

import org.prography.bff.config.exception.badrequest.InvalidRequestException
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.prography.bff.vote.controller.mapper.VoteMapper
import org.prography.bff.vote.controller.model.*
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.prography.bff.vote.service.VoteService
import org.prography.bff.vote.service.model.VoteInfo
import org.prography.bff.vote.service.model.VoteSubmit
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * 투표 관련 서비스 컨트롤러
 */
@RestController
@RequestMapping("/vote")
class VoteControllerImpl(
    private val voteService: VoteService,
) : VoteController {
    @GetMapping("/{id}")
    override fun getPlatformVoteResult(placeId: String): ApiResponse<PlatformVoteResultDto> {
        val infoMap: Map<VotePlatform, VoteInfo> =
            voteService.getVoteResult(placeId).associateBy { it.platform }

        if (infoMap.isEmpty()) {
            return ApiResponse.success(
                PlatformVoteResultDto(
                    voted = false,
                    results = emptyList(),
                ),
            )
        }

        val stats =
            MatchPlatform.entries.map { matchPlatform ->
                val platform = VoteMapper.service(matchPlatform)
                val info = infoMap[platform]

                val categoryMap = info?.categories?.associateBy { it.category } ?: emptyMap()

                val reasons =
                    Reason.entries.map { reason ->
                        val category = VoteMapper.service(reason)
                        val count = categoryMap[category]?.count ?: 0L
                        VoteStat(reason = reason, count = count)
                    }

                VoteResult(
                    platform = matchPlatform,
                    count = info?.total ?: 0L,
                    reasons = reasons,
                )
            }

        return ApiResponse.success(
            PlatformVoteResultDto(
                voted = true,
                results = stats,
            ),
        )
    }

    @PatchMapping("/submit/{id}")
    override fun submitPlatformVote(
        @PathVariable("id") placeId: String,
        @RequestBody dto: PlatformVoteSubmitDto,
    ): ApiResponse<Void> {
        if (dto.reasons.isEmpty()) {
            throw InvalidRequestException.ReasonEmpty()
        }
        voteService.submit(
            placeId = placeId,
            vo =
                VoteSubmit(
                    userId = UUID.randomUUID(),
                    platform = VoteMapper.service(dto.platform),
                    categories = dto.reasons.map { VoteMapper.service(it) },
                ),
        )

        return ApiResponse.success()
    }

    @GetMapping("/summary/{id}")
    override fun getVoteSummary(
        @PathVariable("id") placeId: String,
        @AuthUser userId: UUID?, // NPE 가능성 존재
    ): ApiResponse<VoteSummaryDto> {
        val voteSummary = voteService.getVoteSummary(userId, placeId)
        return ApiResponse.success(
            VoteSummaryDto(
                total = voteSummary.total,
                isUserVoted = voteSummary.isUserVoted,
            ),
        )
    }
}
