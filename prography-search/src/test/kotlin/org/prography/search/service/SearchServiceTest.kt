package org.prography.search.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.prography.search.config.ElasticsearchConfig
import org.prography.search.config.ElasticsearchProperty
import org.springframework.context.annotation.AnnotationConfigApplicationContext

class SearchServiceTest {
    private lateinit var context: AnnotationConfigApplicationContext
    private lateinit var searchService: SearchService

    @BeforeEach
    fun setup() {
        // 1) AnnotationConfigApplicationContext 생성
        context = AnnotationConfigApplicationContext()

        // 2) @Configuration, @Component, @Service 등을 등록
        context.register(ElasticsearchConfig::class.java) // @Configuration
        context.register(ElasticsearchProperty::class.java) // @Component
        context.register(SearchService::class.java) // @Service

        // 3) (필요하다면) 프로퍼티 오버라이드도 가능. 예: System.setProperty("es.host", "127.0.0.1") 등

        // 4) 컨텍스트 초기화(refresh)
        context.refresh()

        // 5) SearchService 빈 가져오기
        searchService = context.getBean(SearchService::class.java)
    }

    @AfterEach
    fun tearDown() {
        context.close()
    }

    @Test
    fun `Keyword Search 테스트`() {
        val keyword = searchService.cursorSearchByKeyword("그릴", 5)
        Assertions.assertNotNull(keyword)
    }
}
