plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")

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
//    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("org.mariadb.jdbc:mariadb-java-client") // MariaDB 드라이버

    // Mongo DB
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    // Health Check
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // Spring Boot Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.25")
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

kapt {
    arguments {
        arg("querydsl.entityAccessors", "true")
    }
}

val generated = file("src/main/generated")

sourceSets {
    main {
        kotlin.srcDirs += generated
    }
}
