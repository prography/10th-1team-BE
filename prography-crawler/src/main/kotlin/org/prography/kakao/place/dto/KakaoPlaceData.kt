package org.prography.kakao.place.dto

import org.prography.kakao.place.domain.KakaoPlaceInfo

/**
 * KakaoPlaceData DTO
 */
data class KakaoPlaceData(
    var address_name: String,
    var category_name: String? = null,
    var id: String,
    var phone: String? = null,
    var place_name: String,
    var road_address_name: String,
    var x: String? = null,
    var y: String? = null,
) {
    companion object {
        private const val ID_SEPARATOR = "@"
    }

    /**
     * 장소 이름과 도로명 주소 기반 고유 ID 생성 (공백은 '_'로 대체)
     */
    fun getDocId(): String {
        val namePart = place_name
        val roadPart = road_address_name
        return "${namePart}$ID_SEPARATOR$roadPart".replace(" ", "_")
    }

    fun toKakaoInfo(): KakaoPlaceInfo {
        return KakaoPlaceInfo(
            addressName = address_name,
            roadAddressName = road_address_name,
            categoryName = category_name!!,
            id = id,
            phone = phone,
            placeName = place_name,
            x = x,
            y = y,
        )
    }
}
