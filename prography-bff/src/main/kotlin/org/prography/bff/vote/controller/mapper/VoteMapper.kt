package org.prography.bff.vote.controller.mapper

import org.prography.bff.vote.controller.model.enumeration.MatchPlatform
import org.prography.bff.vote.controller.model.enumeration.Reason
import org.prography.bff.vote.repository.model.enumeration.VoteCategory
import org.prography.bff.vote.repository.model.enumeration.VotePlatform

/**
 * 투표 관련 매핑 클래스
 */
object VoteMapper {
    /**
     * Controller -> service 계층 이동 매핑
     */
    fun service(platform: MatchPlatform): VotePlatform {
        return when (platform) {
            MatchPlatform.KAKAO -> VotePlatform.KAKAO
            MatchPlatform.NAVER -> VotePlatform.NAVER
        }
    }

    /**
     * Service -> Controller 계층 이동 매핑
     */
    fun controller(platform: VotePlatform): MatchPlatform {
        return when (platform) {
            VotePlatform.KAKAO -> MatchPlatform.KAKAO
            VotePlatform.NAVER -> MatchPlatform.NAVER
        }
    }

    /**
     * Controller -> service 계층 이동 매핑
     */
    fun service(reason: Reason): VoteCategory {
        return when (reason) {
            Reason.MANY_REVIEWS -> VoteCategory.MANY_REVIEWS
            Reason.DETAILED -> VoteCategory.DETAILED
            Reason.HONEST -> VoteCategory.HONEST
            Reason.ACCURATE -> VoteCategory.ACCURATE
        }
    }

    /**
     * Service -> Controller 계층 이동 매핑
     */
    fun controller(category: VoteCategory): Reason {
        return when (category) {
            VoteCategory.MANY_REVIEWS -> Reason.MANY_REVIEWS
            VoteCategory.DETAILED -> Reason.DETAILED
            VoteCategory.HONEST -> Reason.HONEST
            VoteCategory.ACCURATE -> Reason.ACCURATE
        }
    }
}
