import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // 버전만 관리하고, 실제 적용은 서브모듈에서 따로
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa") apply false
    kotlin("kapt") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    // ktlint은 모든 모듈에 적용해서 코드 스타일을 검사
    id("org.jlleitschuh.gradle.ktlint")
}

allprojects {
    group = "com.prography"
    version = "1.0.0"
    repositories { mavenCentral() }
}

subprojects {
    // KotlinCompile 옵션: 거의 모든 모듈에 공통 적용
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "21"
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = "21"
        targetCompatibility = "21"
        options.release.set(21)
    }

    // 테스트 환경: JUnit5 + Sprint Test
    tasks.withType<Test> {
        workingDir = rootProject.projectDir
        useJUnitPlatform()
    }

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        version.set("1.2.1")
    }
    // ktlintCheck 태스크가 존재하는 경우에만 check에 의존을 걸어준다.
    afterEvaluate {
        if (tasks.findByName("ktlintCheck") != null) {
            tasks.named("check") {
                dependsOn("ktlintCheck")
            }
        }
    }
}
