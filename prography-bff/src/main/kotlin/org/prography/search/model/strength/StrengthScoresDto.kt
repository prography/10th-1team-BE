package org.prography.search.model.strength

import org.prography.search.service.model.StrengthScore

data class StrengthScoresDto(
    val strengths: List<StrengthRateDto>,
) {
    companion object {
        fun fromDomain(strengthScores: List<StrengthScore>): StrengthScoresDto {
            return StrengthScoresDto(
                strengthScores.map {
                    StrengthRateDto(
                        it.category.korean,
                        it.kakaoRate,
                        it.naverRate,
                    )
                },
            )
        }
    }
}
