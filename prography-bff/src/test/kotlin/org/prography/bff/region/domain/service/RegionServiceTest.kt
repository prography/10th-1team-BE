package org.prography.bff.region.domain.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class RegionServiceTest
    @Autowired
    constructor(
        private val regionService: RegionService,
    ) {
        /**
         * 테스트 수행 결과
         * DB 조회 시간: 236ms
         * 캐시 조회 시간: 57ms
         */
        @Test
        fun `DB 조회와 캐시 조회 성능 비교`() {
            val testBCode = "1111010300" // 실제 존재하는 bCode
            val iterations = 10_000

            // DB 조회
//        val dbStart = System.currentTimeMillis()
//        repeat(iterations) {
//            regionService.findRegionByBCodeFromDb(testBCode)
//        }
//        val dbDuration = System.currentTimeMillis() - dbStart
//
//        // 캐시 조회
//        val cacheStart = System.currentTimeMillis()
//        repeat(iterations) {
//            regionService.findRegionByBCodeFromCache(testBCode)
//        }
//        val cacheDuration = System.currentTimeMillis() - cacheStart
//
//        println("DB 조회 시간: ${dbDuration}ms")
//        println("캐시 조회 시간: ${cacheDuration}ms")
        }
    }
