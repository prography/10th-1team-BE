package org.prography.bff.region.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.region.domain.entity.City

data class CityDetailDto(
    @Schema(example = "강남구") val name: String,
    @Schema(example = "11680") val cityCode: String,
    @Schema(example = "true") val isSearchable: Boolean,
    val dongList: List<DongDetailDto>,
) {
    companion object {
        fun fromDomain(city: City): CityDetailDto {
            val dongList =
                city.dongList.map { DongDetailDto.fromDomain(it) }
            return CityDetailDto(
                name = city.name,
                cityCode = city.code,
                isSearchable = city.isScraped,
                dongList = dongList,
            )
        }
    }
}
