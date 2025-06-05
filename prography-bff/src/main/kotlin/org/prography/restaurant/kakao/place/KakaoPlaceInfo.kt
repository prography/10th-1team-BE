package org.prography.restaurant.kakao.place

data class KakaoPlaceInfo(
    var addressName: String,
    var roadAddressName: String,
    var categoryName: String,
    var id: String,
    var phone: String? = null,
    var placeName: String,
    var x: String? = null,
    var y: String? = null,
)
