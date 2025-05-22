package org.prography.region.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.region.domain.entity.Province

data class ProvinceDto(
    @Schema(example = "서울특별시") val name: String,
    @Schema(example = "11") val code: String,
    val cities: List<CityDetailDto>,
) {
    companion object {
        fun fromDomain(province: Province): ProvinceDto {
            val citiesDto = province.cities.map { CityDetailDto.fromDomain(it) }
            return ProvinceDto(province.name, province.code, citiesDto)
        }
    }
}
