package org.prography.bff.config.response

data class CursorResponse<T>(
    val content: List<T>,
    val hasNext: Boolean,
)
