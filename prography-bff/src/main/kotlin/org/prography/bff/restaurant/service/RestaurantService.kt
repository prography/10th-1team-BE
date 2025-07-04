package org.prography.bff.restaurant.service

import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.region.domain.service.RegionService
import org.prography.bff.restaurant.RawRestaurantDataRepository
import org.prography.bff.restaurant.kakao.review.KakaoScoreSet
import org.prography.bff.restaurant.naver.review.NaverScoreSet
import org.prography.bff.restaurant.service.model.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantService(
    private val restaurantDataRepository: RawRestaurantDataRepository,
    private val regionService: RegionService,
) {
    fun getPlaceDetail(placeId: String): PlaceDetail {
        val restaurantData =
            restaurantDataRepository.findByIdOrNull(placeId)
                ?: throw NotFoundException.PlaceNotFoundException()

        val naverScoreSet =
            restaurantData.naverReviewData?.score
                ?: throw NotFoundException.PlaceInfoNotFoundException()
        val kakaoScoreSet =
            restaurantData.kakaoReviewData?.score
                ?: throw NotFoundException.PlaceInfoNotFoundException()

        val strengthList = countStrength(kakaoScoreSet, naverScoreSet)
        val dongName = regionService.findRegionByBCode(restaurantData.bCode)

        return PlaceDetail.fromDomain(restaurantData, strengthList, dongName)
    }

    private fun countStrength(
        kakaoScoreSet: KakaoScoreSet,
        naverScoreSet: NaverScoreSet,
    ): List<StrengthScore> {
        // 1) StrengthDescription 별로 count 계산
        val scores =
            StrengthDescription.entries.map { desc ->
                val kakaoCount =
                    kakaoScoreSet.strengthCounts
                        .mapNotNull { strength ->
                            KakaoReviewTag.fromId(strength.id)
                                ?.takeIf { it.description == desc }
                                ?.let { strength.count }
                        }
                        .sum()

                val naverCount =
                    naverScoreSet.strengthCounts
                        .mapNotNull { strength ->
                            NaverReviewTag.fromCode(strength.code)
                                ?.takeIf { it.description == desc }
                                ?.let { strength.count }
                        }
                        .sum()

                StrengthScore(desc, kakaoCount, naverCount)
            }

        // 2) 전체 합
        val totalKakao = scores.sumOf { it.kakaoCount }
        val totalNaver = scores.sumOf { it.naverCount }

        // 3) 비율 계산 (소수점 유지)
        scores.map { score ->
            score.kakaoRate =
                if (totalKakao > 0) {
                    score.kakaoCount.toDouble() / totalKakao
                } else {
                    0.0
                }
            score.naverRate =
                if (totalNaver > 0) {
                    score.naverCount.toDouble() / totalNaver
                } else {
                    0.0
                }
        }
        return scores
    }

    fun getPlaceReview(placeId: String): ReviewList {
        val restaurantData =
            restaurantDataRepository.findByIdOrNull(placeId)
                ?: throw NotFoundException.PlaceNotFoundException()

        return ReviewList.fromDomain(restaurantData)
    }
}
