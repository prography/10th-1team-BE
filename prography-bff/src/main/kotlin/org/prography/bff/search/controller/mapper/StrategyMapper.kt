package org.prography.bff.search.controller.mapper

import org.prography.bff.search.controller.model.enumeration.OrderStrategy
import org.prography.search.service.model.enumeration.SortingStrategy

/**
 * 전략 유형 매핑 클래스
 */
object StrategyMapper {
    /**
     * Controller -> service 계층 이동 매핑
     */
    fun service(strategy: OrderStrategy): SortingStrategy {
        return when (strategy) {
            OrderStrategy.RELATED -> SortingStrategy.RELATED
            OrderStrategy.AVERAGE_RATING_HIGH -> SortingStrategy.AVERAGE_RATING_HIGH
            OrderStrategy.AVERAGE_RATING_LOW -> SortingStrategy.AVERAGE_RATING_LOW
            OrderStrategy.REVIEW_COUNT_HIGH -> SortingStrategy.REVIEW_COUNT_HIGH
            OrderStrategy.REVIEW_COUNT_LOW -> SortingStrategy.REVIEW_COUNT_LOW
        }
    }
}
