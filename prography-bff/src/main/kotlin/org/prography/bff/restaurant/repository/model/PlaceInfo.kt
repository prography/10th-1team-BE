package org.prography.bff.restaurant.repository.model

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Field

data class PlaceInfo(
    @Id
    val id: String,
    @Field("b_code")
    val leaglCode: Int?,
    @Field("kakaoPlaceData.placeName")
    val placeName: String?,
    @Field("kakaoPlaceData.categoryName")
    val categoryName: String?,
    @Field("kakaoPlaceData.roadAddressName")
    val roadAddress: String?,
)
