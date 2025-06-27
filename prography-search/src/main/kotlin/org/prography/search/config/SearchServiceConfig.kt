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
import org.prography.search.config.property.ElasticsearchProperty
import org.prography.search.service.ElasticSearchService
import org.prography.search.service.FakeSearchService
import org.prography.search.service.PlaceSearchService
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Profiles
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLHandshakeException
import kotlin.system.exitProcess

/**
 * 환경(dev, local, prod)에 따라 PlaceSearchService 구현체를 동적으로 등록하는 설정
 */
@Configuration
@EnableConfigurationProperties(ElasticsearchProperty::class)
class SearchServiceConfig(
    private val env: ConfigurableEnvironment,
    private val props: ElasticsearchProperty,
) {
    private val log = LoggerFactory.getLogger(SearchServiceConfig::class.java)

    /**
     * profile 에 따른 서비스에 대한 조건 빈 등록
     */
    @Bean
    fun placeSearchService(): PlaceSearchService {
        return if (!env.acceptsProfiles(Profiles.of("prod", "dev"))) {
            log.info("Running in local profile, returning FakeSearchService")
            FakeSearchService()
        } else {
            log.info("Running in prod / dev profile, returning ElasticSearchService")
            val restClient = restClient()
            val transport = transport(restClient)
            val elasticsearchClient = elasticsearchClient(transport)
            ElasticSearchService(elasticsearchClient)
        }
    }

    /**
     * Low-level REST Client
     */
    private fun restClient(): RestClient {
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
    private fun transport(restClient: RestClient): ElasticsearchTransport {
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
    private fun elasticsearchClient(transport: ElasticsearchTransport): ElasticsearchClient {
        val client = ElasticsearchClient(transport)
        log.info("🚀  ElasticsearchClient initialized")

        try {
            val pingResponse = client.ping()
            if (pingResponse.value()) {
                log.info("✅ Elasticsearch ping successful (cluster is reachable).")
            } else {
                log.error("❌ Elasticsearch ping returned false; cluster might be unreachable.")
                exitProcess(1)
            }
        } catch (sslEx: SSLHandshakeException) {
            // SSL 인증서 검증 실패 시
            log.error("❌ SSL certificate validation failed when pinging Elasticsearch: ${sslEx.localizedMessage}")
            exitProcess(1)
        } catch (e: Exception) {
            // 기타 예상치 못한 예외 (네트워크 오류 등)
            log.error("❌ Failed to connect to Elasticsearch during ping check: ${e.localizedMessage}")
            exitProcess(1)
        }
        return client
    }
}
