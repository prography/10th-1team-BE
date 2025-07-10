package org.prography.bff.bookmark.service.model

import java.time.LocalDateTime
import java.util.UUID

data class BookmarkPlace(
    val id: UUID,
    val placeId: String,
    val placeName: String,
    val roadAddress: String,
    val category: String,
    val legal: Int,
    val savedAt: LocalDateTime,
)
