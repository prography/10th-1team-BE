package org.prography.bff.restaurant

import org.prography.bff.restaurant.kakao.place.KakaoPlaceInfo
import org.prography.bff.restaurant.kakao.review.KakaoReviewData
import org.prography.bff.restaurant.naver.place.NaverPlaceInfo
import org.prography.bff.restaurant.naver.review.NaverReviewData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "restaurant_data")
data class RawRestaurantData(
    @Id
    var id: String? = null,
    val dongCode: String,
    var kakaoPlaceData: KakaoPlaceInfo? = null,
    var naverPlaceData: NaverPlaceInfo? = null,
    var kakaoReviewData: KakaoReviewData? = null,
    var naverReviewData: NaverReviewData? = null,
)
