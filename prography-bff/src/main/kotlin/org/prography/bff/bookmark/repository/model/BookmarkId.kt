package org.prography.bff.bookmark.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
data class BookmarkId(
    @Column(name = "GROUP_ID")
    val groupId: UUID,
    @Column(name = "PLACE_ID")
    val placeId: String,
) : Serializable
