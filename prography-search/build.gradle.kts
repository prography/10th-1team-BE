plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.data:spring-data-elasticsearch:5.5.0")
    implementation("co.elastic.clients:elasticsearch-java:8.18.1")
}
