package org.prography.config.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Elasticsearch configuration component
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperty::class)
class ElasticsearchConfig(
    private val props: ElasticsearchProperty,
) {
    /**
     * Low-level REST Client
     */
    @Bean
    fun retClient(): RestClient {
        val schema = if (props.tls) "https" else "http"
        return RestClient.builder(
            HttpHost(props.host, props.port, schema),
        ).build()
    }

    /**
     * Transport layer for Java client
     */
    @Bean
    fun transport(restClient: RestClient): ElasticsearchTransport =
        RestClientTransport(
            restClient,
            JacksonJsonpMapper(),
        )

    /**
     * High-level Java client
     */
    @Bean
    fun elasticsearchClient(transport: ElasticsearchTransport): ElasticsearchClient = ElasticsearchClient(transport)
}
