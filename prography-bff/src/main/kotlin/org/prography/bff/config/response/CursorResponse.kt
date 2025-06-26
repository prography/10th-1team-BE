package org.prography.bff.config.response

data class CursorResponse<T>(
    val total: Long,
    val content: List<T>,
    val cursor: String? = null,
    val hasNext: Boolean,
)
