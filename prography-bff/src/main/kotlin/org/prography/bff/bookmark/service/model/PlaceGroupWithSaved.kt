package org.prography.bff.bookmark.service.model

import java.util.UUID

data class PlaceGroupWithSaved(
    val placeGroups: List<PlaceGroup>,
    val savedGroupIds: List<UUID>,
)
