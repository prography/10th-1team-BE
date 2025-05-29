package org.prography.kakao.region.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coord2AddressDocument(
    val regionType: String? = null,
    val code: String? = null,
    val addressName: String? = null,
    val region1Depth: String? = null,
    val region2Depth: String? = null,
    val region3Depth: String? = null,
    val region4Depth: String? = null,
    val x: Double? = null,
    val y: Double? = null,
)
