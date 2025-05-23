package org.prography.config.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.slf4j.LoggerFactory
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
    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Low-level REST Client
     */
    @Bean
    fun retClient(): RestClient {
        val schema = if (props.tls) "https" else "http"
        log.info("🗄️  Elasticsearch RestClient will connect to {}://{}:{}", schema, props.host, props.port)
        return RestClient.builder(
            HttpHost(props.host, props.port, schema),
        ).build()
    }

    /**
     * Transport layer for Java client
     */
    @Bean
    fun transport(restClient: RestClient): ElasticsearchTransport {
        log.info("🔗  Wrapping RestClient in RestClientTransport with JacksonJsonpMapper")
        val objectMapper =
            ObjectMapper()
                .registerModules(kotlinModule())
        return RestClientTransport(
            restClient,
            JacksonJsonpMapper(objectMapper),
        )
    }

    /**
     * High-level Java client
     */
    @Bean
    fun elasticsearchClient(transport: ElasticsearchTransport): ElasticsearchClient {
        log.info("🚀  ElasticsearchClient initialized")
        return ElasticsearchClient(transport)
    }
}
