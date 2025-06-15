package org.prography.bff.config.response

data class CursorResponse<T>(
    val content: List<T>,
    val cursor: String? = null,
    val hasNext: Boolean,
)
