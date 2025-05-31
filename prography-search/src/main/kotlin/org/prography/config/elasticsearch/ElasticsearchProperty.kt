package org.prography.config.elasticsearch

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Elasticsearch 관련 설정 값
 */
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
data class ElasticsearchProperty(
    /**
     * TLS 적용 여부
     * DEFAULT VALUE = false
     */
    val tls: Boolean,
    /**
     * 연결 HOST 주소 e.g localhost, 127.0.0.1
     * DEFAULT VALUE = localhost
     */
    val host: String,
    /**
     * 연결 PORT 값
     * DEFAULT VALUE = 9300
     */
    val port: Int,
    /**
     * user 값, TLS 가 적용되는 경우에만 사용
     */
    val user: String,
    /**
     * password 값, TLS 가 적용되는 경우에만 사용
     */
    val password: String,
)
