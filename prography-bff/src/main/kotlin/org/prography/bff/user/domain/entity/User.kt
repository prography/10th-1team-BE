package org.prography.bff.user.domain.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
class User(provider: Provider, providerId: String, nickname: String) {
    @Id
    val id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    val provider: Provider = provider

    val providerId: String = providerId

    var nickname: String = nickname
        protected set
}
