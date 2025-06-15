package org.prography.search.domain

/**
 * Elasticsearch GEO point 타입
 */
data class GeoPoint(
    /**
     * 위도
     */
    val lat: Double,
    /**
     * 경도
     */
    val lon: Double,
)
