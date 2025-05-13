package org.prography.restaurant.controller

import org.prography.geo.service.GeoRectSliceService
import org.prography.kakao.place.service.RestaurantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController("/restaurants")
class RestaurantController(
    private val geoRectSliceService: GeoRectSliceService,
    private val restaurantService: RestaurantService,
) {
    @GetMapping("/trigger")
    fun scrapPlaceData(
        @RequestParam gu: String,
    ) {
        val dongListByGu = geoRectSliceService.getDongListByGu(gu)
        dongListByGu.forEach {
            val rects =
                geoRectSliceService.sliceRectFromFeature(
                    it,
                    0.001,
                )
            val dongCode =
                geoRectSliceService.getDongCodeFromAdmName(
                    it,
                )
            rects.map { rect ->
                restaurantService.searchDataAsync(dongCode, rect)
            }
        }
    }
}
