package org.prography.bff.restaurant.service.model

enum class StrengthDescription(
    val korean: String,
) {
    FLAVOR("음식 만족도"),
    PRICE("가격 만족도"),
    SERVICE("서비스"),
    MOOD("분위기"),
}
