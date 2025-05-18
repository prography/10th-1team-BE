package org.prography.naver.place.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Place(
    /** 응답 JSON 키가 그대로 "list" 이므로 명시적으로 매핑 */
    @JsonProperty("list")
    val list: List<PlaceItem> = emptyList(),
)
