plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("org.locationtech.jts:jts-core:1.19.0")
}
