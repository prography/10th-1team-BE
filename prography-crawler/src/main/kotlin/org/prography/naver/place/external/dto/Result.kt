package org.prography.naver.place.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Result(
    val place: Place? = null,
)
