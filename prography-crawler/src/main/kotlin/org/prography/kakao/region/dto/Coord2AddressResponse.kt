package org.prography.kakao.region.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// 2) coord2address 응답 매핑용 DTO
@JsonIgnoreProperties(ignoreUnknown = true)
data class Coord2AddressResponse(
    val meta: Meta? = null,
    val documents: List<Coord2AddressDocument>,
)
