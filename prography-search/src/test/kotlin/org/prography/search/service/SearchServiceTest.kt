package org.prography.search.service

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.prography.search.config.ElasticsearchConfig
import org.prography.search.config.ElasticsearchProperty
import org.prography.search.service.model.PlaceSearchResult
import org.prography.search.service.model.enumeration.FilterCategory
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
    fun `Filter Search 테스트`() {
        val data = searchService.cursorSearchByKeyword("제육", size = 4, addressCodes = emptyList(), cursorString = "")
        val filtered =
            data.result.filter { r -> r.category != FilterCategory.FD01.value }
                .toList()
        Assertions.assertEquals(0, filtered.size)
    }

    @Test
    fun `Keyword Search 테스트`() {
        val allResults = mutableListOf<PlaceSearchResult>()
        var cursorString: String? = null

        do {
            // 첫 요청에는 cursorString == null
            val page =
                searchService.cursorSearchByKeyword(
                    keyword = "제육",
                    size = 5,
                    cursorString = cursorString,
                    addressCodes = emptyList(),
                )

            // 결과 누적
            allResults += page.result

            // 다음 cursor 갱신: hasNext 가 true 일 때만 cursorString 에 담고, 아니면 null 로 끊기
            cursorString = if (page.hasNext) page.cursor else null
        } while (cursorString != null)

        Assertions.assertEquals(5, allResults.size)
    }

    @Test
    fun `AutoComplete Search 테스트`() {
        val keyword = searchService.autoCompleteByKeyword("용두동", 5, emptyList())
    }
}
