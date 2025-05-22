package org.prography.search.service.model

/**
 * 리뷰 태그 코드 목록
 */
enum class NaverReviewTag(val code: String, val description: StrengthDescription) {
    FOOD_GOOD("food_good", StrengthDescription.FLAVOR),
    KIND("kind", StrengthDescription.SERVICE),
    EAT_ALONE("eat_alone", StrengthDescription.MOOD),
    LARGE("large", StrengthDescription.SERVICE),
    STORE_CLEAN("store_clean", StrengthDescription.SERVICE),
    SPACIOUS("spacious", StrengthDescription.SERVICE),
    FRESH("fresh", StrengthDescription.FLAVOR),
    SPECIAL_MENU("special_menu", StrengthDescription.FLAVOR),
    PRICE_CHEAP("price_cheap", StrengthDescription.PRICE),

    //    PARKING_EASY("parking_easy"),
    INTERIOR_COOL("interior_cool", StrengthDescription.SERVICE),
    TOILET_CLEAN("toilet_clean", StrengthDescription.SERVICE),
    TOGETHER("together", StrengthDescription.SERVICE),
    TASTE_HEALTHY("taste_healthy", StrengthDescription.FLAVOR),
    VIEW_GOOD("view_good", StrengthDescription.SERVICE),
    FOOD_FAST("food_fast", StrengthDescription.FLAVOR),
    SPECIAL_DAY("special_day", StrengthDescription.SERVICE),
    MENU_GOOD("menu_good", StrengthDescription.FLAVOR),
    ATMOSPHERE_CALM("atmosphere_calm", StrengthDescription.SERVICE),
    ;

    //    KID_GOOD("kid_good");

    companion object {
        // code 문자열 → Enum 으로 변환할 때 사용
        private val map = entries.associateBy(NaverReviewTag::code)

        fun fromCode(code: String): NaverReviewTag? = map[code]
    }
}
