package org.prography.search.service.model

enum class KakaoReviewTag(val id: Int, val description: StrengthDescription) {
    FLAVOR(5, StrengthDescription.FLAVOR),
    PRICE(1, StrengthDescription.PRICE),
    SERVICE(2, StrengthDescription.SERVICE),
    MOOD(3, StrengthDescription.MOOD),
    ;

    companion object {
        // code 문자열 → Enum 으로 변환할 때 사용
        private val map = KakaoReviewTag.entries.associateBy(KakaoReviewTag::id)

        fun fromId(id: Int): KakaoReviewTag? = map[id]
    }
}
