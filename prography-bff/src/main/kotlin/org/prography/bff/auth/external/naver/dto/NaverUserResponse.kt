package org.prography.bff.auth.external.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverUserResponse(
    @JsonProperty("resultcode")
    val resultCode: String,
    val message: String,
    val response: NaverUserInfo,
)
