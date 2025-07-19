package org.prography.bff.bookmark.controller.model

import java.util.UUID

data class UpdatePlaceAtGroup(
    val userId: UUID,
    val placeId: String,
    val groupdIds: Set<UUID>,
)
