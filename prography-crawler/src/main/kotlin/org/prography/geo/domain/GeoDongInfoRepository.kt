package org.prography.geo.domain

import org.springframework.data.mongodb.repository.MongoRepository

interface GeoDongInfoRepository : MongoRepository<GeoDongInfo, String> {
}