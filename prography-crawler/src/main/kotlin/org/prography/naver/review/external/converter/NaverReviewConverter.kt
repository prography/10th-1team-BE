package org.prography.naver.review.external.converter

import org.prography.naver.review.domain.*
import org.prography.naver.review.external.dto.res.*
import org.springframework.stereotype.Service

@Service
class NaverReviewConverter {
    fun toDomain(response: List<NaverReviewResponse>): NaverReviewData {
        var naverReviews: List<NaverReview> = mutableListOf()
        var naverScoreSet: NaverScoreSet? = null

        response.forEach { naverReviewResponse ->
            val data = naverReviewResponse.data
            if (data.visitorReviews != null) {
                naverReviews = data.visitorReviews.toDomain()
            }
            if (data.visitorReviewStats != null) {
                naverScoreSet = data.visitorReviewStats.toDomain()
            }
        }
        return NaverReviewData(
            reviews = naverReviews,
            score = naverScoreSet,
        )
    }

    private fun VisitorReviewStats.toDomain(): NaverScoreSet {
        return NaverScoreSet(
            reviewRating = review?.avgRating,
            totalCount = review?.totalCount ?: 0,
            votedTotalCount = analysis?.votedKeyword?.totalCount ?: 0,
            votedReviewCount = analysis?.votedKeyword?.reviewCount ?: 0,
            strengthCounts = analysis?.votedKeyword?.details?.map { it.toDomain() }.orEmpty(),
        )
    }

    private fun VotedKeywordDetail.toDomain(): NaverStrengthCount {
        return NaverStrengthCount(
            code = code,
            count = count,
        )
    }

    private fun VisitorReviews.toDomain(): List<NaverReview> {
        return items.map { item ->
            NaverReview(
                id = item.id,
                reviewId = item.reviewId,
                originType = item.originType.orEmpty(),
                rating = item.rating,
                body = item.body.orEmpty(),
                visitCount = item.visitCount,
                medias = item.media.orEmpty().map { it.toDomain() },
                naverOwnerData = item.author.toDomain(),
                registeredAt = item.created,
            )
        }
    }

    private fun Author.toDomain(): NaverOwnerData {
        return NaverOwnerData(
            id = id,
            nickname = nickname,
            imageUrl = imageUrl,
        )
    }

    private fun MediaItem.toDomain(): NaverMedia {
        return NaverMedia(
            type = type,
            thumbnail = thumbnail,
            videoUrl = videoUrl.orEmpty(),
        )
    }
}
