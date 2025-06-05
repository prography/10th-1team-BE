package org.prography.kakao.region.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Coord2AddressDocument(
    @JsonProperty("region_type")
    val regionType: String? = null,
    @JsonProperty("code")
    val code: String? = null,
    @JsonProperty("address_name")
    val addressName: String? = null,
    @JsonProperty("region_1depth_name")
    val region1Depth: String? = null,
    @JsonProperty("region_2depth_name")
    val region2Depth: String? = null,
    @JsonProperty("region_3depth_name")
    val region3Depth: String? = null,
    @JsonProperty("region_4depth_name")
    val region4Depth: String? = null,
    @JsonProperty("x")
    val x: Double? = null,
    @JsonProperty("y")
    val y: Double? = null,
)
