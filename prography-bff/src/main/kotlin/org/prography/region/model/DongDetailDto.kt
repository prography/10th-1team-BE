package org.prography.region.model

import io.swagger.v3.oas.annotations.media.Schema
import org.prography.region.domain.entity.Dong

data class DongDetailDto(
    @Schema(example = "역삼1동") val name: String,
    @Schema(example = "1168064000") val dongCode: String,
) {
    companion object {
        fun fromDomain(dong: Dong): DongDetailDto {
            return DongDetailDto(
                name = dong.name,
                dongCode = dong.code,
            )
        }
    }
}
