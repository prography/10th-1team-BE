package org.prography.naver.place.service

import org.prography.kakao.place.domain.KakaoPlaceInfo
import org.prography.naver.place.external.NaverFeignClient
import org.prography.naver.place.external.dto.PlaceItem
import org.prography.naver.place.util.StoreNameNormalizer
import org.prography.restaurant.domain.RawRestaurantData
import org.prography.restaurant.domain.RawRestaurantDataRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NaverPlaceService(
    private val naverFeignClient: NaverFeignClient,
    private val rawRestaurantDataRepository: RawRestaurantDataRepository,
    private val storeNameNormalizer: StoreNameNormalizer,
) {
    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    /** Java style Executor + CompletableFuture */
    fun saveNaverPlaceData(restaurantData: RawRestaurantData) {
        if (restaurantData.kakaoPlaceData == null) {
            log.info("No kakao place found for ${restaurantData.id}")
            return
        }

        val naverInfo = findNaverInfo(restaurantData.kakaoPlaceData!!)
        if (naverInfo != null) {
            log.info("NaverInfo Found: {}", naverInfo.id)
            restaurantData.naverPlaceData = naverInfo.toNaverPlaceInfo()
            rawRestaurantDataRepository.save(restaurantData)
            return
        }

        log.info("NaverPlace Not Found : ${restaurantData.id}")
    }

    /** 1차 검색 실패 시 fallback 한 번 더 시도 */
    private fun findNaverInfo(kakao: KakaoPlaceInfo): PlaceItem? {
        val coord = "${kakao.x};${kakao.y}"

        fun search(query: String) =
            naverFeignClient
                .searchNaverInfo(query, "all", coord)
                .result
                ?.place
                ?.list
                ?.firstOrNull()

        val primaryQuery = storeNameNormalizer.buildSearchQuery(kakao)
        search(primaryQuery)?.let { return it }

        val fallbackQuery = storeNameNormalizer.buildFallbackSearchQuery(kakao)
        log.info("Fallback query: {}, basic: {}", fallbackQuery, kakao.placeName)
        return search(fallbackQuery)
    }
}
