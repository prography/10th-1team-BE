package org.prography.restaurant.domain

import org.prography.kakao.place.domain.KakaoPlaceInfo
import org.prography.kakao.review.external.dto.KakaoReviewResponse
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "restaurant_data")
data class RawRestaurantData(
    @Id
    var id: String? = null,
    val dongCode: String,

    var kakaoPlaceData: KakaoPlaceInfo? = null,
//    var naverPlaceData: PlaceItem? = null,
    var kakaoReviewResponse: KakaoReviewResponse? = null,
//    var naverReviewResponse: NaverReviewResponse? = null
) {
    var kakaoReviewProcessed: Boolean = false
    var naverReviewProcessed: Boolean = false
}
