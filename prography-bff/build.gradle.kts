plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}