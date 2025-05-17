package org.prography.naver.place.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/** 최상위 응답 ─ naverPlaceClient.searchPlace() */
@JsonIgnoreProperties(ignoreUnknown = true)
data class NaverPlaceData(
    val result: Result? = null,
)
