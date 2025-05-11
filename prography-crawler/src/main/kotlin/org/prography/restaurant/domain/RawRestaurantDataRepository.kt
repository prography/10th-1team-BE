package org.prography.restaurant.domain

import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository

interface RawRestaurantDataRepository : MongoRepository<RawRestaurantData, String> {
    fun countByKakaoReviewProcessedFalse(): Long
    fun countByNaverReviewProcessedFalse(): Long
    fun findByKakaoReviewProcessedFalse(pageRequest: PageRequest): List<RawRestaurantData>
    fun findByNaverReviewProcessedFalse(pageRequest: PageRequest): List<RawRestaurantData>
}