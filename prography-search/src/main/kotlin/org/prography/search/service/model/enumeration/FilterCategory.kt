package org.prography.search.service.model.enumeration

/**
 *
 */
enum class FilterCategory(val values: List<String>) {
    KOREAN(listOf("한식", "족발,보쌈", "갈비", "찌개,전골", "국밥", "칼국수", "냉면", "감자탕")),
    JAPANESE(listOf("일식", "돈까스,우동", "초밥,롤", "일식집", "일본식라면")),
    CHINESE(listOf("중식", "중국요리", "양꼬치")),
    WESTERN(listOf("양식", "이탈리안")),
    SNACK(listOf("분식", "국수", "떡볶이", "순대")),
    CAFE_BAKERY(listOf("제과,베이커리", "간식", "떡,한과", "아이스크림", "파리바게뜨")),
    FAST_FOOD(listOf("패스트푸드", "피자", "햄버거", "도시락")),
    SALAD(listOf("샐러드", "샌드위치")),
    MEAT(listOf("육류,고기", "치킨", "닭요리", "곱창,막창", "삼겹살", "샤브샤브")),
    SEAFOOD(listOf("해물,생선", "회", "참치회")),
    PUB(listOf("호프,요리주점", "술집", "일본식주점", "실내포장마차", "칵테일바", "와인바")),
    WORLD_CUISINE(listOf("아시아음식", "동남아음식", "퓨전요리", "뷔페", "베트남음식", "장어", "멕시칸,브라질")),
    ;

    companion object {
        fun fromKeyword(keyword: String): FilterCategory? =
            entries.firstOrNull { category ->
                category.values.any { it == keyword }
            }
    }
}
