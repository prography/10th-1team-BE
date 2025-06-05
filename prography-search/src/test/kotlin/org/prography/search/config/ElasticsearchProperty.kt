package org.prography.search.config

import org.prography.search.config.elasticsearch.ElasticsearchProperty.SslProperties
import org.springframework.stereotype.Component

@Component
class ElasticsearchProperty {
    val host: String = "localhost"
    val port: Int = 9200
    val user: String = "elastic"
    val password: String = "changeme"
    var ssl: SslProperties = SslProperties(enable = true, trustAll = true)
}
