package org.prography.bff.auth.external.naver.dto

data class NaverUserInfo(
    val id: String,
    val nickname: String?,
    val email: String?,
    val name: String?,
)
