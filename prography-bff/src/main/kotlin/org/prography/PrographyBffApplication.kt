package org.prography

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["org.prography.bff", "org.prography.search"])
@SpringBootApplication(scanBasePackages = ["org.prography.bff", "org.prography.search"])
class PrographyBffApplication

fun main(args: Array<String>) {
    runApplication<PrographyBffApplication>(*args)
}
