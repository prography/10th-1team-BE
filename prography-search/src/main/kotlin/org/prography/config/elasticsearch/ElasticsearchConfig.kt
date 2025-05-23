package org.prography.config.elasticsearch

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration

@Configuration
@EnableConfigurationProperties(ElasticsearchProperty::class)
class ElasticsearchConfig(
    private val props: ElasticsearchProperty,
) : ElasticsearchConfiguration() {
    override fun clientConfiguration(): ClientConfiguration {
        val address = "${props.host}:${props.port}"
        val builder =
            ClientConfiguration.builder()
                .connectedTo(address)

        if (props.tls) {
            builder.usingSsl()
        }

        return builder.build()
    }
}
