package org.prography.search.config.elasticsearch

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Elasticsearch 관련 설정 값
 */
@ConfigurationProperties(prefix = "spring.data.elasticsearch")
data class ElasticsearchProperty(
    /**
     * 연결 HOST 주소 e.g localhost, 127.0.0.1
     * DEFAULT VALUE = localhost
     */
    val host: String = "localhost",
    /**
     * 연결 PORT 값
     * DEFAULT VALUE = 9200
     */
    val port: Int = 9200,
    /**
     * user 값, TLS 가 적용되는 경우에만 사용
     */
    val user: String = "elastic",
    /**
     * password 값, TLS 가 적용되는 경우에만 사용
     */
    val password: String = "changeme",
    /**
     * ssl 설정 그룹
     */
    var ssl: SslProperties = SslProperties(),
) {
    /**
     * ssl: { enable, trust-all } 과 매핑)
     */
    data class SslProperties(
        /**
         * TLS(HTTPS) 연결을 활성화할지 여부
         * (application-local.yml: spring.data.elasticsearch.ssl.enable)
         */
        var enable: Boolean = true,
        /**
         * TLS가 활성화된 상태에서 "모든 인증서"를 무조건 신뢰할지(true)/기본 검증(false)할지 여부
         * (application-local.yml: spring.data.elasticsearch.ssl.trust-all)
         */
        var trustAll: Boolean = false,
    )
}
