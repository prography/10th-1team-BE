package org.prography.bff.vote.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.vote.controller.mapper.VoteMapper
import org.prography.bff.vote.controller.model.PlatformVoteResultDto
import org.prography.bff.vote.controller.model.PlatformVoteSubmitDto
import org.prography.bff.vote.service.VoteService
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
        TODO("Not yet implemented")
    }

    @PatchMapping("/submit/{id}")
    override fun submitPlatformVote(
        @PathVariable("id") placeId: String,
        @RequestBody dto: PlatformVoteSubmitDto,
    ): ApiResponse<Void> {
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
}
