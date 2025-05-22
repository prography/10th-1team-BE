package org.prography.search.service.mock

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.prography.config.exception.badrequest.InvalidRequestException
import org.prography.config.exception.notfound.NotFoundException
import org.prography.restaurant.RawRestaurantDataRepository
import org.prography.restaurant.kakao.review.KakaoScoreSet
import org.prography.restaurant.naver.review.NaverScoreSet
import org.prography.search.service.model.*
import org.springframework.core.io.ClassPathResource
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MockService(
    private val restaurantDataRepository: RawRestaurantDataRepository,
) {
    private val objectMapper = jacksonObjectMapper()
    private val mockSummaryData: List<PlaceSummary> by lazy {
        val resource = ClassPathResource("updated_mock.json")
        resource.inputStream.use { inputStream ->
            objectMapper.readValue(inputStream)
        }
    }

    fun getSummaries(
        keyword: String?,
        lastId: String?,
        size: Int,
    ): List<PlaceSummary> {
        val fromIndex = lastId?.let { getStatIndex(lastId) } ?: 0
        return if (keyword.isNullOrBlank()) {
            findAllSummaries(fromIndex, size)
        } else {
            findFilteredSummaries(keyword, fromIndex, size)
        }
    }

    private fun findAllSummaries(
        fromIndex: Long,
        size: Int,
    ): List<PlaceSummary> {
        if (fromIndex >= mockSummaryData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockSummaryData.size.toLong())
        return mockSummaryData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    private fun findFilteredSummaries(
        keyword: String,
        fromIndex: Long,
        size: Int,
    ): List<PlaceSummary> {
        if (fromIndex >= mockSummaryData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockSummaryData.size.toLong())
        return mockSummaryData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    private fun getStatIndex(lastId: String): Long {
        if (!existsBySummaryId(lastId)) {
            throw InvalidRequestException.CursorInvalidRequest()
        }

        val index = mockSummaryData.indexOfFirst { it.id == lastId }
        require(index != -1) { "getStatIndex called with non-existent lastId: $lastId" }

        return (index + 1).toLong()
    }

    private fun existsBySummaryId(id: String?): Boolean = id?.let { cursorId -> mockSummaryData.any { it.id == cursorId } } ?: true

    fun isLatest(lastId: String): Boolean {
        val index = mockSummaryData.indexOfFirst { it.id == lastId }
        return index + 1 < mockSummaryData.size
    }

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

        return PlaceDetail.fromDomain(restaurantData, strengthList)
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
}
