package org.prography.search.service.model

data class CursorAutoComplete(
    val result: List<AutoComplete> = emptyList(),
    val hasNext: Boolean,
)
