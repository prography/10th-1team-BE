package org.prography.config.elasticsearch

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.elasticsearch")
data class ElasticsearchProperty(
    val tls: Boolean,
    val host: String,
    val port: Int,
)
