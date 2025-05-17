package org.prography.kakao.review.external.converter // ← 철자 수정: converter

import org.prography.kakao.review.domain.*
import org.prography.kakao.review.external.dto.*

/**
 * Kakao API 응답 DTO → 도메인 모델 매핑기
 *
 * - object 싱글턴을 유지해 간편 호출 (`KakaoReviewDataConverter.toDomain(resp)`)
 * - 반환형 명시 : `KakaoReviewData`
 * - `!!` 제거, `orEmpty()`/ 기본값으로 NPE 방어
 * - 컬렉션은 일관되게 빈 List 반환
 */
object KakaoReviewDataConverter {
    /** 최상위 변환 함수 (외부 공개) */
    fun toDomain(response: KakaoReviewResponse): KakaoReviewData =
        KakaoReviewData(
            score = response.scoreSet!!.toDomain(),
            reviews = response.reviews.orEmpty().map { it.toDomain() },
        )

    // ---------- 내부 확장 함수( private ) ----------

    private fun ScoreSet.toDomain() =
        KakaoScoreSet(
            reviewCount = reviewCount,
            totalScore = totalScore,
            averageScore = averageScore,
            strengthCounts = strengthCounts,
            strengthUv = strengthUv,
        )

    private fun Review.toDomain() =
        KakaoReview(
            reviewId = reviewId,
            starRating = starRating,
            content = contents.orEmpty(),
            photoCount = photoCount,
            photos = photos.orEmpty().map { it.toDomain() },
            strengthIds = strengthIds.orEmpty(),
            registeredAt = registeredAt,
            updatedAt = updatedAt,
            ownerData = meta.owner.toDomain(),
        )

    private fun Photo.toDomain() =
        KakaoPhoto(
            url = url,
            photoId = photoId,
            reviewId = reviewId,
            updatedAt = updatedAt,
        )

    private fun Owner.toDomain() =
        KakaoOwnerData(
            id = id,
            nickname = nickname.orEmpty(),
            profileImageUrl = profileImageUrl.orEmpty(),
            reviewCount = reviewCount ?: 0,
            averageScore = averageScore ?: 0.0,
        )
}
