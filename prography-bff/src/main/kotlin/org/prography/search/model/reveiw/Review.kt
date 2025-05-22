package org.prography.search.model.reveiw

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.restaurant.kakao.review.KakaoReview
import org.prography.restaurant.naver.review.NaverReview

data class Review(
    @Schema(example = "ReviewID") val id: String,
    @Schema(example = "낭만고라니") val author: String,
    @Schema(example = "http://th-p.talk.kakao.co.kr/th/talkp/wkO60Hi4gC/Kkimcd9RLaHSydAzHnK2uk/n47lhr.jpg") val authorImageUrl: String?,
    @Schema(
        example = "2025-04-01 20:08:21",
        description = "카카오 네이버의 String 형태 다름",
    ) val registeredAt: String,
    @Schema(example = "비싸지만 맛은 평범한곳..그러나! 새벽까지 운영하고 주차장 넓고  분위기가 겁나 깡패. 분위기가 진짜 개깡패 너무 이쁨") val contents: String,
) {
    companion object {
        fun fromNaverReview(naverReview: NaverReview): Review {
            return Review(
                id = naverReview.reviewId,
                author = naverReview.naverOwnerData.nickname,
                authorImageUrl = naverReview.naverOwnerData.imageUrl,
                registeredAt = naverReview.registeredAt,
                contents = naverReview.body,
            )
        }

        fun fromKakaoReview(kakaoReview: KakaoReview): Review {
            return Review(
                id = kakaoReview.reviewId.toString(),
                author = kakaoReview.ownerData.nickname,
                authorImageUrl = kakaoReview.ownerData.profileImageUrl,
                registeredAt = kakaoReview.updatedAt,
                contents = kakaoReview.content,
            )
        }
    }
}
