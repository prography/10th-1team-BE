package org.prography.bff.bookmark.service.model

import java.time.LocalDateTime
import java.util.UUID

data class PlaceGroup(
    val id: UUID,
    val name: String,
    val icon: String,
    val total: Long,
    val createdAt: LocalDateTime,
    val savedAt: LocalDateTime,
)
