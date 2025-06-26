package org.prography.bff.auth.external.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfo(
    val id: String,
    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?,
)
