package org.prography.bff.restaurant.service.model

/**
 * 리뷰 태그 코드 목록
 */
enum class NaverReviewTag(val code: String, val description: StrengthDescription) {
    FOOD_GOOD("food_good", StrengthDescription.FLAVOR), // 음식이 맛있어요
    FRESH("fresh", StrengthDescription.FLAVOR), // 재료가 신선해요
    SPECIAL_MENU("special_menu", StrengthDescription.FLAVOR), // 특별한 메뉴가 있어요
    TASTE_HEALTHY("taste_healthy", StrengthDescription.FLAVOR), // 건강한 맛이에요
    MENU_GOOD("menu_good", StrengthDescription.FLAVOR), // 메뉴 구성이 알차요
    PRICE_WORTHY("price_worthy", StrengthDescription.FLAVOR), // 비싼 만큼 가치있어요
    SIDEDISH_GOOD("sidedish_good", StrengthDescription.FLAVOR), // 기본 안주가 좋아요
    SNACK_GOOD("snack_good", StrengthDescription.FLAVOR), // ??
    ALCOHOL_BAR("alcoh_bar", StrengthDescription.FLAVOR), // 술이 다양해요
    DESSERT_GOOD("dessert_good", StrengthDescription.FLAVOR), // 디저트가 맛있어요
    COURSE_GOOD("course_good", StrengthDescription.FLAVOR), // 코스요리가 알차요
    DRINK_GOOD("drink_good", StrengthDescription.FLAVOR), // 음료가 맛있어요
    COFFEE_GOOD("coffee_good", StrengthDescription.FLAVOR), // 커피가 맛있어요
    MEAT_GOOD("meat_good", StrengthDescription.FLAVOR), // 고기 질이 좋아요
    FOOD_FAST("food_fast", StrengthDescription.FLAVOR), // 음식이 빨리 나와요
    LOCAL_TASTE("local_taste", StrengthDescription.FLAVOR), // 현지맛에 가까워요
    SPICE_WEAK("spice_weak", StrengthDescription.FLAVOR), // 향신료가 강하지 않아요
    LESS_SMELL("less_smell", StrengthDescription.FLAVOR), // 잡내가 적어요

    SALADBAR_GOOD("saladbar_good", StrengthDescription.FLAVOR), // 샐러드 바가 잘 되어있어요

    PRICE_CHEAP("price_cheap", StrengthDescription.PRICE), // 가성비가 좋아요

    KIND("kind", StrengthDescription.SERVICE), // 친절해요
    STAFF_COOK("staff_cook", StrengthDescription.SERVICE), // 직접 잘 구워줘요
    PACKAGING_CLEAN("packaging_clean", StrengthDescription.SERVICE), // 포장이 깔끔해요
    SPACIOUS("spacious", StrengthDescription.SERVICE), // ??

    INTERIOR_COOL("interior_cool", StrengthDescription.MOOD), // 인테리어가 멋져요
    LARGE("large", StrengthDescription.MOOD), // 매장이 넓어요
    EAT_ALONE("eat_alone", StrengthDescription.MOOD), // 혼밥하기 좋아요
    STORE_CLEAN("store_clean", StrengthDescription.MOOD), // 매장이 깨끗해요
    TOILET_CLEAN("toilet_clean", StrengthDescription.MOOD), // 화장실이 꺠끗해요
    TOGETHER("together", StrengthDescription.MOOD), // 단체모임 하기 좋아요
    VIEW_GOOD("view_good", StrengthDescription.MOOD), // 뷰가 좋아요
    COZY("cozy", StrengthDescription.MOOD), // 아늑해요
    OUTDOOR_GOOD("outdoor_good", StrengthDescription.MOOD), // 야외공간이 멋져요
    CONCEPT_UNIQUE("concept_unique", StrengthDescription.MOOD), // 컨셉이 독특해요
    TALK_GOOD("talk_good", StrengthDescription.MOOD), // 대화하기 좋아요
    PHOTO_GOOD("photo_good", StrengthDescription.MOOD), // 사진이 잘나와요
    MUSIC_GOOD("music_good", StrengthDescription.MOOD), // 음악이 좋아요
    STUDY_GOOD("study_good", StrengthDescription.MOOD), // 집중하기 좋아요
    DRINK_ALONE("drink_alone", StrengthDescription.MOOD), // 혼술하기 좋아요

    SPECIAL_DAY("special_day", StrengthDescription.MOOD), // 특별한 날 가기 좋아요
    ROOM_NICE("room_nice", StrengthDescription.MOOD), // 룸이 잘되어있어요
    STAY_LONG("stay_long", StrengthDescription.MOOD), // 오래 머무르기 좋아요
    COMFY("comfy", StrengthDescription.MOOD), // 좌석이 편해요
    VENTILATION_GOOD("ventilation_good", StrengthDescription.MOOD), // 환기가 잘 돼요
    ATMOSPHERE_CALM("atmosphere_calm", StrengthDescription.MOOD), // 차분한 분위기에요
    ;

    //    KID_GOOD("kid_good");

    //    PARKING_EASY("parking_easy"),

    companion object {
        // code 문자열 → Enum 으로 변환할 때 사용
        private val map = entries.associateBy(NaverReviewTag::code)

        fun fromCode(code: String): NaverReviewTag? = map[code]
    }
}
