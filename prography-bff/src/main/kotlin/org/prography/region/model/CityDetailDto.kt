package org.prography.region.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.region.domain.entity.City

data class CityDetailDto(
    @Schema(example = "강남구") val name: String,
    @Schema(example = "1168000000") val cityCode: String,
    @Schema(example = "true") val isSearchable: Boolean,
) {
    companion object {
        fun fromDomain(city: City): CityDetailDto =
            CityDetailDto(
                city.name,
                city.code,
                isSearchable = city.isScraped,
            )
    }
}
