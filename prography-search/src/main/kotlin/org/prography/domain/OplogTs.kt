package org.prography.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * oplog_ts 의 T, I 필드를 담는 클래스
 */
data class OplogTs(
    @JsonProperty("T")
    val T: Long = 0,
    @JsonProperty("I")
    val I: Long = 0,
)
