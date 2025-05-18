package org.prography.kakao.place.service

import org.prography.kakao.place.dto.KakaoPlaceData
import org.prography.kakao.place.external.KakaoFeignClient
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

@Service
class RestaurantService(
    @Qualifier("mainScrapExecutor")
    private val executor: Executor,
    private val kakaoFeignClient: KakaoFeignClient,
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
) {
    companion object {
        private val log = LoggerFactory.getLogger(RestaurantService::class.java)
        private const val FOOD_CATEGORY_CODE = "FD6"
        private const val PAGE_MAX_SIZE = 15
        private const val THREAD_SLEEP_MILLIS = 10_000L
        private const val KAKAO_PAGE_SIZE_LIMIT = 45
    }

//    fun scrapAllRestaurants(scrapScaleDto: ScrapScaleDto) {
//        val minLat = scrapScaleDto.getMin_latitude()
//        val maxLat = scrapScaleDto.getMax_latitude()
//        val minLng = scrapScaleDto.getMin_longitude()
//        val maxLng = scrapScaleDto.getMax_longitude()
//
//        val futures = mutableListOf<CompletableFuture<Void>>()
//        var lat = minLat
//        while (lat < maxLat) {
//            var lng = minLng
//            while (lng < maxLng) {
//                val fromLat = lat
//                val toLat = minOf(lat + SMALL_SCALE_FACTOR, maxLat)
//                val fromLng = lng
//                val toLng = minOf(lng + SMALL_SCALE_FACTOR, maxLng)
//                futures += searchDataAsync(fromLat, fromLng, toLat, toLng)
//                lng += SMALL_SCALE_FACTOR
//            }
//            lat += SMALL_SCALE_FACTOR
//        }
//
//        CompletableFuture.allOf(*futures.toTypedArray()).join()
//        log.info("All restaurant scraping tasks completed.")
//    }

    fun searchDataAsync(
        dongCode: String,
        rectParam: String,
    ): CompletableFuture<Void> =
        CompletableFuture.runAsync({
            try {
                sleepThread()
                log.info("check All From {}", rectParam)
                val placeList = getAllRestaurantsInRect(rectParam)
                log.info("Found {} restaurant data.", placeList.size)
                placeList.forEach { savePlace(dongCode, it) }
            } catch (e: Exception) {
                log.error(
                    "Error in range: [{}] - {}",
                    rectParam,
                    e.message,
                    e,
                )
            }
        }, executor)

    private fun sleepThread() {
        try {
            Thread.sleep(THREAD_SLEEP_MILLIS)
        } catch (ie: InterruptedException) {
            Thread.currentThread().interrupt()
            log.warn("Thread sleep was interrupted", ie)
        }
    }

    private fun getAllRestaurantsInRect(rectParam: String): List<KakaoPlaceData> {
        val kakaoPlaces = mutableListOf<KakaoPlaceData>()
        var page = 1
        val firstResponse =
            kakaoFeignClient.searchByCategory(
                FOOD_CATEGORY_CODE,
                page,
                rectParam,
            )
        if (firstResponse.meta.total_count > KAKAO_PAGE_SIZE_LIMIT) {
            log.error(
                "Place Size is {} (in {})",
                firstResponse.meta.total_count,
                rectParam,
            )
        }
        kakaoPlaces += firstResponse.documents
        val endPage =
            Math.ceil(firstResponse.meta.total_count.toDouble() / PAGE_MAX_SIZE).toInt()
        for (p in 2..endPage) {
            kakaoPlaces +=
                kakaoFeignClient
                    .searchByCategory(
                        FOOD_CATEGORY_CODE,
                        p,
                        rectParam,
                    )
                    .documents
        }
        return kakaoPlaces
    }

    private fun savePlace(
        dongCode: String,
        kakaoPlaceData: KakaoPlaceData,
    ) {
        val id = kakaoPlaceData.getDocId()
        if (isAlreadySaved(id)) {
            log.info("Document with id {} already exists.", id)
            return
        }
        val data =
            RawRestaurantData(
                id = id,
                dongCode = dongCode,
                kakaoPlaceData = kakaoPlaceData.toKakaoInfo(),
            )
        rawRestaurantDataRepository.save(data)
    }

    private fun isAlreadySaved(docId: String): Boolean = rawRestaurantDataRepository.existsById(docId)
}
