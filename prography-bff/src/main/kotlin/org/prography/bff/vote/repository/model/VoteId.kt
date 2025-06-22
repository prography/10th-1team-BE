package org.prography.bff.vote.repository.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.prography.bff.vote.repository.model.enumeration.VotePlatform
import java.io.Serializable

/**
 * 상호명 + 플랫폼 으로 구성된 복합키
 */
@Embeddable
class VoteId(
    /**
     * 상호명
     */
    @Column(name = "PLACE_ID")
    val id: String,
    /**
     * 플랫폼 이름
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PLATFORM_NAME")
    val platform: VotePlatform,
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VoteId

        if (id != other.id) return false
        if (platform != other.platform) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + platform.hashCode()
        return result
    }
}
