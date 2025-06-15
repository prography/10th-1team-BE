package org.prography.bff.search.controller.mapper

import org.prography.bff.search.controller.model.enumeration.FoodCategory
import org.prography.search.service.model.enumeration.FilterCategory

/**
 * 음식 유형 매핑 클래스
 */
object CategoryMapper {
    /**
     * Service -> Controller 계층 이동 매핑
     */
    fun controller(category: FilterCategory?): FoodCategory {
        if (category == null) {
            return FoodCategory.UNDEFINED
        }
        return when (category) {
            FilterCategory.KOREAN -> FoodCategory.FD01
            FilterCategory.JAPANESE -> FoodCategory.FD02
            FilterCategory.CHINESE -> FoodCategory.FD03
            FilterCategory.WESTERN -> FoodCategory.FD04
            FilterCategory.SNACK -> FoodCategory.FD05
            FilterCategory.CAFE_BAKERY -> FoodCategory.FD06
            FilterCategory.FAST_FOOD -> FoodCategory.FD07
            FilterCategory.SALAD -> FoodCategory.FD08
            FilterCategory.MEAT -> FoodCategory.FD09
            FilterCategory.SEAFOOD -> FoodCategory.FD10
            FilterCategory.PUB -> FoodCategory.FD11
            FilterCategory.WORLD_CUISINE -> FoodCategory.FD12
        }
    }

    /**
     * Controller -> Service 계층 이동 매핑
     */
    fun service(category: FoodCategory): FilterCategory? {
        return when (category) {
            FoodCategory.UNDEFINED -> null
            FoodCategory.FD01 -> FilterCategory.KOREAN
            FoodCategory.FD02 -> FilterCategory.JAPANESE
            FoodCategory.FD03 -> FilterCategory.CHINESE
            FoodCategory.FD04 -> FilterCategory.WESTERN
            FoodCategory.FD05 -> FilterCategory.SNACK
            FoodCategory.FD06 -> FilterCategory.CAFE_BAKERY
            FoodCategory.FD07 -> FilterCategory.FAST_FOOD
            FoodCategory.FD08 -> FilterCategory.SALAD
            FoodCategory.FD09 -> FilterCategory.MEAT
            FoodCategory.FD10 -> FilterCategory.SEAFOOD
            FoodCategory.FD11 -> FilterCategory.PUB
            FoodCategory.FD12 -> FilterCategory.WORLD_CUISINE
        }
    }
}
