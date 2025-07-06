package org.prography.bff.restaurant.repository

import org.springframework.data.mongodb.core.mapping.Field

data class PlaceInfo(
    @Field("kakaoPlaceData.placeName")
    val placeName: String?,
    @get:Field("kakaoPlaceData.categoryName")
    val categoryName: String?,
)
