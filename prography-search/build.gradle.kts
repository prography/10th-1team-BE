plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")

    implementation("org.springframework.data:spring-data-elasticsearch:5.5.0")
    implementation("co.elastic.clients:elasticsearch-java:8.18.1")
}
