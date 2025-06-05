rootProject.name = "10th-1team-BE"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        // Kotlin
        // Kotlin 컴파일
        kotlin("jvm") version "1.9.25"
        kotlin("plugin.spring") version "1.9.25"
        kotlin("plugin.jpa") version "1.9.25"
        kotlin("kapt") version "1.9.25"

        // Spring Boot
        id("org.springframework.boot") version "3.4.4"
        id("io.spring.dependency-management") version "1.1.4"

        // ktlint
        id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
    }
}

include(
    "prography-crawler",
    "prography-search",
    "prography-bff",
)
