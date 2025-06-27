package org.prography.search.config

import co.elastic.clients.elasticsearch.ElasticsearchClient
import org.prography.search.service.ElasticSearchService
import org.prography.search.service.FakeSearchService
import org.prography.search.service.PlaceSearchService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Profiles

/**
 * 환경(dev, local, prod)에 따라 PlaceSearchService 구현체를 동적으로 등록하는 설정
 */
@Configuration
class PlaceSearchServiceConfig(
    private val env: ConfigurableEnvironment,
    private val esClient: ElasticsearchClient,
) {
    private val log = LoggerFactory.getLogger(PlaceSearchServiceConfig::class.java)

    @Bean
    fun placeSearchService(): PlaceSearchService {
        return if (env.acceptsProfiles(Profiles.of("dev", "local"))) {
            log.info("🛠 Running in dev/local profile, returning FakeSearchService")
            FakeSearchService()
        } else {
            log.info("🚀 Running in prod/staging, returning ElasticSearchService")
            ElasticSearchService(esClient)
        }
    }
}
