package org.prography.bff.restaurant

import org.prography.bff.restaurant.kakao.place.KakaoPlaceInfo
import org.prography.bff.restaurant.kakao.review.KakaoReviewData
import org.prography.bff.restaurant.naver.place.NaverPlaceInfo
import org.prography.bff.restaurant.naver.review.NaverReviewData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "restaurant_data")
data class RawRestaurantData(
    @Id
    var id: String? = null,
    @Field("h_code")
    val hCode: String,
    @Field("b_code")
    val bCode: String,
    var kakaoPlaceData: KakaoPlaceInfo,
    var naverPlaceData: NaverPlaceInfo? = null,
    var kakaoReviewData: KakaoReviewData? = null,
    var naverReviewData: NaverReviewData? = null,
)
