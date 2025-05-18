package org.prography.naver.place.util

import org.prography.kakao.place.domain.KakaoPlaceInfo
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class StoreNameNormalizer {
    /** 지점·본점을 가리키는 한국어 접미사 */
    private val branchSuffixes =
        listOf(
            "본점",
            "[가-힣]{1,10}점", // 강남점·서초점 …
            "[가-힣]{1,10}지점", // 영등포지점·성남지점 …
        )

    private val branchRegex: Pattern =
        Pattern.compile("\\s*(${branchSuffixes.joinToString("|")})\\s*$")

    /** 매장 이름 정규화: 지점/본점/○○점 접미사 제거 (미매칭 시 원본 리턴) */
    private fun normalize(raw: String): String {
        val m = branchRegex.matcher(raw)
        return if (m.find()) {
            m.replaceFirst("").trimEnd()
        } else {
            raw
        }
    }

    fun buildSearchQuery(kakao: KakaoPlaceInfo): String = "${kakao.placeName} ${kakao.roadAddressName}"

    fun buildFallbackSearchQuery(kakao: KakaoPlaceInfo): String = "${normalize(kakao.placeName)} ${kakao.roadAddressName}"
}
