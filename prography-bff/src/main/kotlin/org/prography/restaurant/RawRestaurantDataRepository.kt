package org.prography.restaurant

import org.springframework.data.mongodb.repository.MongoRepository

interface RawRestaurantDataRepository : MongoRepository<RawRestaurantData, String>
