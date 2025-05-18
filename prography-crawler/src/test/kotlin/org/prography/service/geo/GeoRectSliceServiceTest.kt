package org.prography.service.geo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.prography.geo.service.GeoRectSliceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GeoRectSliceServiceTest {
    @Autowired
    private lateinit var geoRectSliceService: GeoRectSliceService

    @Test
    fun gangnam_gu_Test() {
        val dongListByGu = geoRectSliceService.getDongListByGu("서울특별시 강남구")

        assertThat(dongListByGu.size).isEqualTo(22)
//        서울특별시 강남구 신사동
//        서울특별시 강남구 논현1동
//        서울특별시 강남구 논현2동
//        서울특별시 강남구 삼성1동
//        서울특별시 강남구 삼성2동
//        서울특별시 강남구 대치1동
//        서울특별시 강남구 대치4동
//        서울특별시 강남구 역삼1동
//        서울특별시 강남구 역삼2동
//        서울특별시 강남구 도곡1동
//        서울특별시 강남구 도곡2동
//        서울특별시 강남구 개포1동
//        서울특별시 강남구 개포4동
//        서울특별시 강남구 일원본동
//        서울특별시 강남구 일원1동
//        서울특별시 강남구 개포3동
//        서울특별시 강남구 수서동
//        서울특별시 강남구 세곡동
//        서울특별시 강남구 압구정동
//        서울특별시 강남구 청담동
//        서울특별시 강남구 대치2동
//        서울특별시 강남구 개포2동
    }
}
