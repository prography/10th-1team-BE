package org.prography.config.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
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
        val schema = if (props.tls) "https" else HttpHost.DEFAULT_SCHEME_NAME

        log.info("🗄Elasticsearch RestClient will connect to {}://{}:{}", schema, props.host, props.port)

        if (!props.tls) {
            return RestClient.builder(HttpHost(props.host, props.port, schema)).build()
        }
        val credentialsProvider =
            BasicCredentialsProvider().apply {
                setCredentials(
                    AuthScope.ANY,
                    UsernamePasswordCredentials(props.user, props.password),
                )
            }

        return RestClient.builder(HttpHost(props.host, props.port, schema))
            .setHttpClientConfigCallback { callBack ->
                callBack.setDefaultCredentialsProvider(credentialsProvider)
            }.build()
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
