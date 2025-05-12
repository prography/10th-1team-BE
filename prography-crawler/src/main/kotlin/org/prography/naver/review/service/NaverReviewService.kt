package org.prography.naver.review.service

import org.prography.naver.place.domain.NaverPlaceInfo
import org.prography.naver.review.external.NaverReviewFeignClient
import org.prography.naver.review.external.converter.NaverReviewConverter
import org.prography.naver.review.external.dto.req.GraphQLRequest
import org.prography.naver.review.external.dto.req.GraphQLVariables
import org.prography.naver.review.external.dto.req.ReviewStatsVariables
import org.prography.naver.review.external.dto.req.VisitorReviewsInput
import org.prography.naver.review.external.dto.res.NaverReviewResponse
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NaverReviewService(
    private val naverReviewConverter: NaverReviewConverter,
    private val naverReviewFeignClient: NaverReviewFeignClient,
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)

        /** GraphQL 쿼리 문자열 (raw-string → $, 줄바꿈 그대로) */
        private const val REVIEW_QUERY = """
            query getVisitorReviews(${'$'}input: VisitorReviewsInput) {
              visitorReviews(input: ${'$'}input) {
                total
                starDistribution { score count }
                items {
                  id reviewId originType created language translatedText
                  rating body tags
                  media { type thumbnail videoUrl }
                  author { id nickname imageUrl }
                  viewCount visitCount
                  reactionStat { totalCount typeCount { name count } }
                  hasViewerReacted { reacted }
                }
              }
            }
        """

        private const val VISITOR_REVIEW_STATS_QUERY = """
            query getVisitorReviewStats(${'$'}id: String, ${'$'}itemId: String, ${'$'}businessType: String = "place") {
              visitorReviewStats(
                input: {businessId: ${'$'}id, itemId: ${'$'}itemId, businessType: ${'$'}businessType}
              ) {
                id
                review { avgRating totalCount }
                analysis {
                  votedKeyword { totalCount reviewCount userCount 
                    details { code count }
                  }
                }
              }
            }
        """
    }

    /** 모든 RawRestaurantData 에 대해 리뷰를 병렬 수집 (Executor + CompletableFuture) */
    fun saveNaverReview(rawRestaurantData: RawRestaurantData) {
        log.info("naverReviewSearchStart = {}", rawRestaurantData.id)

        val placeInfo: NaverPlaceInfo = rawRestaurantData.naverPlaceData ?: return
        val naverReviewData = findNaverInfo(placeInfo.id)

        if (naverReviewData.isNotEmpty()) {
            log.info(
                "naverReviewSize = {}",
                naverReviewData.first().data.visitorReviews?.total,
            )
            val naverReviewDomain = naverReviewConverter.toDomain(naverReviewData)
            rawRestaurantData.naverReviewData = naverReviewDomain
            rawRestaurantDataRepository.save(rawRestaurantData)
            return
        }
        log.info("naverReviewNotFound = {}", rawRestaurantData.id)
    }

    /** 단일 사업장 리뷰 조회 */
    fun findNaverInfo(businessId: String): List<NaverReviewResponse> {
        val reviewVariables = VisitorReviewsInput(businessId, "restaurant", 1, 50)
        val variables = GraphQLVariables(reviewVariables)
        val visitorReviewRequest =
            GraphQLRequest(
                operationName = "getVisitorReviews",
                query = REVIEW_QUERY,
                variables = variables,
            )

        val reviewStatsVariables =
            ReviewStatsVariables(businessType = "restaurant", id = businessId, itemId = "0")
        val reviewStatRequest =
            GraphQLRequest(
                operationName = "getVisitorReviewStats",
                query = VISITOR_REVIEW_STATS_QUERY,
                variables = reviewStatsVariables,
            )

        return naverReviewFeignClient.getVisitorReviews(
            "recent",
            listOf(visitorReviewRequest, reviewStatRequest),
        )
    }
}
