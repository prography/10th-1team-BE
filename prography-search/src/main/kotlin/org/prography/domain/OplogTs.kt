package org.prography.domain

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * oplog_ts 의 T, I 필드를 담는 클래스
 */
data class OplogTs(
    /**
     * 초 단위 Unix 타임스탬프
     */
    @JsonProperty("T")
    val T: Long = 0,
    /**
     * 동일 초 내에서의 카운터
     */
    @JsonProperty("I")
    val I: Long = 0,
)
