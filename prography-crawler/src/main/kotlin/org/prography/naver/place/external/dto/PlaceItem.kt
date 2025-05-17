package org.prography.naver.place.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import org.prography.naver.place.domain.NaverPlaceInfo

/**
 * 네이버 장소 검색 결과의 단일 아이템
 *
 * 주석 처리된 필드는 응답에 따라 언제든 추가/제거할 수 있습니다.
 * 필요한 경우 주석을 해제하고 타입을 지정해 주세요.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class PlaceItem(
    // 기본 식별/표시
    // val index: String? = null,
    // val rank: String? = null,
    val id: String? = null, // "163636452"
    val name: String? = null,
    // val display: String? = null,
    // 연락처
    // val tel: String? = null,
    // val telDisplay: String? = null,
    // val isCallLink: Boolean? = null,
    // val virtualTel: String? = null,
    // val virtualTelDisplay: String? = null,
    // 카테고리
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val category: List<String> = emptyList(),
    // 위치
    val address: String? = null,
    @JsonProperty("roadAddress")
    val roadAddress: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val shortAddress: List<String> = emptyList(),
    val x: Double? = null,
    val y: Double? = null,
    // val distance: String? = null,
    // 미디어/리뷰
    val thumUrl: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val thumUrls: List<String> = emptyList(),
    val reviewCount: Int = 0,
    val placeReviewCount: Int = 0,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val microReview: List<String> = emptyList(),
    // 영업 정보
    // val businessStatus: BusinessStatus? = null,
    // val bizhourInfo: String? = null,
    val menuInfo: String? = null,
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val context: List<String> = emptyList(), // 가게 키워드
    // 파노라마/마커
    // val marker: String? = null,
    // val markerSelected: String? = null,
    // val markerId: String? = null,
    // val posExact: String? = null,
    // val itemLevel: String? = null,
    // 편의·예약
    // val menuExist: String? = null,
    // val hasNaverBooking: Boolean? = null,
    // val hasNaverSmartOrder: Boolean? = null,
    // val coupon: String? = null,
) {
    fun toNaverPlaceInfo(): NaverPlaceInfo {
        return NaverPlaceInfo(
            id = id!!,
            name = name!!,
            category = category,
            address = address!!,
            roadAddress = roadAddress!!,
            shortAddress = shortAddress,
            thumUrl = thumUrl,
            thumUrls = thumUrls,
            reviewCount = reviewCount,
            microReview = microReview,
            menuInfo = menuInfo,
            context = context,
            x = x,
            y = y,
            placeReviewCount = placeReviewCount,
        )
    }
}
