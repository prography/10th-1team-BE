plugins {
    // 라이브러리 모듈로 빌드 (JAR 생성)
    `java-library`
    kotlin("jvm")
    kotlin("plugin.spring")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    // Spring
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.4.4"))
    implementation("org.springframework.boot:spring-boot")

    // Elasticsearch
    implementation("org.springframework.data:spring-data-elasticsearch:5.5.0")
    implementation("co.elastic.clients:elasticsearch-java:8.18.1")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
