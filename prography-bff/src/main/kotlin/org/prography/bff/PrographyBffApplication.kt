package org.prography.bff

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.prography.bff", "org.prography.search"])
class PrographyBffApplication

fun main(args: Array<String>) {
    runApplication<PrographyBffApplication>(*args)
}
