package org.prography.search.service.model.enumeration

/**
 *
 */
enum class FilterCategory(val categories: List<String>) {
    FD01(listOf("한식")),
    FD02(listOf("일식")),
    FD03(listOf("양식")),
    FD04(listOf("중식")),
    FD05(listOf("분식")),
    FD06(listOf("베트남")),
    FD07(listOf("야식")),
    ;

    companion object {
        private val codeMap = FilterCategory.values().associateBy { it.name }

        fun ofCode(code: String): FilterCategory? = codeMap[code]

        fun getKeywordsByCode(code: String): List<String> = ofCode(code)?.categories.orEmpty()
    }
}
