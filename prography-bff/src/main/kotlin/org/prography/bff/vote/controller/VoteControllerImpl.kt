package org.prography.bff.vote.controller

import org.prography.bff.config.exception.badrequest.InvalidRequestException
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.prography.bff.vote.controller.mapper.VoteMapper
import org.prography.bff.vote.controller.model.VoteResultDto
import org.prography.bff.vote.controller.model.VoteSubmitDto
import org.prography.bff.vote.controller.model.VoteSummaryDto
import org.prography.bff.vote.controller.model.composite.VoteRecord
import org.prography.bff.vote.controller.model.composite.VotedResult
import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import org.prography.bff.vote.service.VoteService
import org.prography.bff.vote.service.model.PlatformResultVo
import org.prography.bff.vote.service.model.SubmitVo
import org.prography.bff.vote.service.model.composite.PlatformVoteInfo
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
    override fun getPlatformVoteResult(
        @AuthUser userId: UUID?,
        @PathVariable("id") placeId: String,
    ): ApiResponse<VoteResultDto> {
        if (placeId.isBlank()) {
            throw IllegalArgumentException()
        }

        val vo: PlatformResultVo = voteService.getVoteResult(userId, placeId)
        val platformMap: Map<VotePlatform, PlatformVoteInfo> =
            vo.result.associateBy { it.platform }

        if (platformMap.isEmpty()) {
            return ApiResponse.success(
                VoteResultDto(
                    record = null,
                    results = emptyMap(),
                ),
            )
        }

        val results: Map<MatchPlatform, VotedResult> =
            MatchPlatform.entries.associateWith { matchPlatform ->
                val info: PlatformVoteInfo? = platformMap[VoteMapper.service(matchPlatform)]
                if (info == null) {
                    VotedResult(
                        reasons = Reason.entries.associateWith { 0L },
                    )
                } else {
                    val reasons =
                        Reason.entries.associateWith { reason ->
                            val categoryMap = info.categories.associateBy { it.category }
                            val category = VoteMapper.service(reason)
                            categoryMap[category]?.count ?: 0L
                        }

                    VotedResult(
                        count = info.total,
                        reasons = reasons,
                    )
                }
            }

        return ApiResponse.success(
            VoteResultDto(
                total = platformMap.values.sumOf { it.total },
                voted = vo.history != null,
                record =
                    vo.history?.let {
                        VoteRecord(
                            platform = VoteMapper.controller(it.platform),
                            reason =
                                it.reasons.map { reason ->
                                    VoteMapper.controller(reason)
                                },
                            votedDate = it.votedDate,
                        )
                    },
                results = results,
            ),
        )
    }

    @PatchMapping("/submit/{id}")
    override fun submitPlatformVote(
        @AuthUser userId: UUID,
        @PathVariable("id") placeId: String,
        @RequestBody dto: VoteSubmitDto,
    ): ApiResponse<Void> {
        if (dto.reasons.isEmpty()) {
            throw InvalidRequestException.ReasonEmpty()
        }

        voteService.submit(
            placeId = placeId,
            vo =
                SubmitVo(
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
                voted = voteSummary.isUserVoted,
            ),
        )
    }
}
