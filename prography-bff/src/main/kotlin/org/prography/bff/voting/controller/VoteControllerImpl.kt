package org.prography.bff.voting.controller

import org.prography.bff.config.response.ApiResponse
import org.prography.bff.voting.controller.model.PlatformVoteDto
import org.prography.bff.voting.controller.model.PlatformVoteResultDto

/**
 * 투표 관련 서비스 컨트롤러
 */
class VoteControllerImpl : VoteController {
    override fun getPlatformVoteResult(placeId: String): ApiResponse<PlatformVoteResultDto> {
        TODO("Not yet implemented")
    }

    override fun submitPlatformVote(
        placeId: String,
        dto: PlatformVoteDto,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }
}
