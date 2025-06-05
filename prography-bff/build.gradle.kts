plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    // Module
    implementation(project(":prography-search"))

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Postgresql
    runtimeOnly("org.postgresql:postgresql")

    // Mongo DB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks {
    named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = true
    }
    named<Jar>("jar") {
        enabled = true
    }
}
