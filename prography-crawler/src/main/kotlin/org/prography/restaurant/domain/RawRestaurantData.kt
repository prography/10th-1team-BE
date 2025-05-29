package org.prography.restaurant.domain

import org.prography.kakao.place.domain.KakaoPlaceInfo
import org.prography.kakao.review.domain.KakaoReviewData
import org.prography.naver.place.domain.NaverPlaceInfo
import org.prography.naver.review.domain.NaverReviewData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "restaurant_data")
data class RawRestaurantData(
    @Id
    var id: String,
    @Field("h_code")
    var hCode: String,
    @Field("b_code")
    var bCode: String,
    var kakaoPlaceData: KakaoPlaceInfo,
    var naverPlaceData: NaverPlaceInfo? = null,
    var kakaoReviewData: KakaoReviewData? = null,
    var naverReviewData: NaverReviewData? = null,
) {
    var kakaoReviewProcessed: Boolean = false
    var naverReviewProcessed: Boolean = false
}
