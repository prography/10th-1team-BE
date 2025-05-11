rootProject.name = "10th-1team-BE"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

include(
    "prography-crawler",
    "prography-search",
    "prography-bff",
)
