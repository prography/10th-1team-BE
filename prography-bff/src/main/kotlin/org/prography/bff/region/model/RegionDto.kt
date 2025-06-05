package org.prography.bff.region.model

import org.prography.bff.region.domain.entity.Province

data class RegionDto(
    val provinces: List<ProvinceDto>,
) {
    companion object {
        fun fromDomain(provinces: List<Province>): RegionDto {
            val provincesDto = provinces.map { ProvinceDto.fromDomain(it) }
            return RegionDto(provincesDto)
        }
    }
}
