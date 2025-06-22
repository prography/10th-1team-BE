package org.prography.bff.user.domain.service

import kotlin.random.Random

object NicknameGenerator {
    private val foodList =
        listOf(
            "떡볶이",
            "짜장면",
            "김밥",
            "비빔밥",
            "불고기",
            "삼겹살",
            "치킨",
            "피자",
            "라면",
            "순두부",
            "된장찌개",
            "김치찌개",
            "갈비탕",
            "설렁탕",
            "냉면",
            "칼국수",
            "쌀국수",
            "돈까스",
            "우동",
            "초밥",
            "오므라이스",
            "햄버거",
            "파스타",
            "스테이크",
            "샐러드",
            "감자탕",
            "족발",
            "보쌈",
            "닭갈비",
            "쭈꾸미",
            "곱창",
            "막창",
            "순대",
            "튀김",
            "만두",
            "잡채",
            "해물파전",
            "부대찌개",
            "토스트",
            "샌드위치",
            "탕수육",
            "양념치킨",
            "후라이드치킨",
            "마라탕",
            "훠궈",
            "양꼬치",
            "초계국수",
            "콩국수",
            "닭개장",
            "동태찌개",
        )

    fun generate(): String {
        val food = foodList.random()
        val number = Random.nextInt(0, 10000).toString().padStart(4, '0')
        return "$food$number"
    }
}
