package org.prography.config.elasticsearch

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.TrustAllStrategy
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.ssl.SSLContexts
import org.elasticsearch.client.RestClient
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.net.ssl.SSLContext

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
        val sslProperties = props.ssl
        val schema = if (sslProperties.enable) "https" else HttpHost.DEFAULT_SCHEME_NAME

        log.info("🗄Elasticsearch RestClient will connect to {}://{}:{}", schema, props.host, props.port)
        if (!sslProperties.enable) {
            return RestClient.builder(HttpHost(props.host, props.port, schema)).build()
        }
        val credentialsProvider =
            BasicCredentialsProvider().apply {
                setCredentials(
                    AuthScope.ANY,
                    UsernamePasswordCredentials(props.user, props.password),
                )
            }

        val builder = RestClient.builder(HttpHost(props.host, props.port, schema))
        return if (sslProperties.trustAll) {
            log.info("🔓 SSL 검증을 무시하도록 RestClient 설정 (elasticsearch.ssl.trust-all=true)")
            val sslContext: SSLContext =
                SSLContexts.custom()
                    .loadTrustMaterial(null, TrustAllStrategy.INSTANCE) // 모든 인증서 무조건 신뢰
                    .build()

            builder.setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .setDefaultCredentialsProvider(credentialsProvider)
            }.build()
        } else {
            log.info("🔐 기본 SSL 검증 모드로 RestClient 설정 (elasticsearch.ssl.trust-all=false)")
            builder.setHttpClientConfigCallback { httpClientBuilder ->
                httpClientBuilder
                    .setDefaultCredentialsProvider(credentialsProvider)
            }.build()
        }
    }

    /**
     * Transport layer for Java client
     */
    @Bean
    fun transport(restClient: RestClient): ElasticsearchTransport {
        log.info("🔗  Wrapping RestClient in RestClientTransport with JacksonJsonMapper")
        val objectMapper =
            ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
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
