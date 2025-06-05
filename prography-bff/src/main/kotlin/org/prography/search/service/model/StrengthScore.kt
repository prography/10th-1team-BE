package org.prography.search.service.model

data class StrengthScore(
    val category: StrengthDescription, // enum class StrengthDescription(val label: String)
    val kakaoCount: Int,
    val naverCount: Int,
) {
    var kakaoRate: Double = 0.0
    var naverRate: Double = 0.0
}
