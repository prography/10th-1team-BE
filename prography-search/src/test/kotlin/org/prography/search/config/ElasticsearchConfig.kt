package org.prography.search.config

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
import org.prography.search.exception.ElasticsearchException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLHandshakeException

/**
 * Elasticsearch configuration component
 */
@Configuration
class ElasticsearchConfig(
    private val props: ElasticsearchProperty,
) {
    private val log = LoggerFactory.getLogger(ElasticsearchConfig::class.java)

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
            log.info("🔓 RestClient SSL ignore setting (elasticsearch.ssl.trust-all=true)")
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
            log.info("🔐 RestClient setting (elasticsearch.ssl.trust-all=false)")
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
        val client = ElasticsearchClient(transport)
        log.info("🚀  ElasticsearchClient initialized")

        try {
            val pingResponse = client.ping()
            if (pingResponse.value()) {
                log.info("✅ Elasticsearch ping successful (cluster is reachable).")
            } else {
                log.error("❌ Elasticsearch ping returned false; cluster might be unreachable.")
                throw ElasticsearchException.ConnectionException(
                    RuntimeException("Elasticsearch ping returned false; cluster unreachable."),
                )
//                System.err.println("FATAL: Elasticsearch cluster unreachable (ping returned false). Exiting.")
//                exitProcess(1)
            }
        } catch (sslEx: SSLHandshakeException) {
            // SSL 인증서 검증 실패 시
            log.error("❌ SSL certificate validation failed when pinging Elasticsearch: ${sslEx.localizedMessage}")
            throw ElasticsearchException.CertificateValidationException(sslEx)
//            System.err.println("FATAL: Elasticsearch SSL certificate validation failed. Exiting.")
//            exitProcess(1)
        } catch (e: Exception) {
            // 기타 예상치 못한 예외 (네트워크 오류 등)
            log.error("❌ Failed to connect to Elasticsearch during ping check: ${e.localizedMessage}")
            throw ElasticsearchException.ConnectionException(e)
//            System.err.println("FATAL: Unable to connect to Elasticsearch (${e.localizedMessage}). Exiting.")
//            exitProcess(1)
        }
        return client
    }
}
