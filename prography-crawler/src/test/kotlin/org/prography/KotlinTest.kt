package org.prography

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinTest {
    @Autowired
    private lateinit var kotlinController: KotlinController

    @Test
    fun helloTest() {
        println(kotlinController.hello())
    }
}