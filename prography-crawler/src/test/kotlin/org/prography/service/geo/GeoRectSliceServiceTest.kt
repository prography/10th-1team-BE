package org.prography.service.geo

import org.junit.jupiter.api.Test
import org.prography.geo.service.GeoRectSliceService
import org.prography.kakao.place.service.RestaurantService
import org.prography.kakao.review.service.KakaoReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CompletableFuture

@SpringBootTest
class GeoRectSliceServiceTest {
    @Autowired
    private lateinit var kakaoReviewService: KakaoReviewService

    @Autowired
    private lateinit var restaurantService: RestaurantService

    @Autowired
    private lateinit var geoRectSliceService: GeoRectSliceService

    @Test
    fun sliceTest() {
        val rects =
            geoRectSliceService.sliceRectFromFeature(
                "서울특별시 종로구 사직동",
                0.001,
            )
        val dongCode =
            geoRectSliceService.getDongCodeFromAdmName(
                "서울특별시 종로구 사직동",
            )

        // searchDataAsync 가 CompletableFuture<Unit> 을 반환한다고 가정
        val futures =
            rects.map { rect ->
                restaurantService.searchDataAsync(dongCode, rect)
            }

        CompletableFuture.allOf(*futures.toTypedArray()).join() // 💡 전부 끝날 때까지 대기
    }
}
