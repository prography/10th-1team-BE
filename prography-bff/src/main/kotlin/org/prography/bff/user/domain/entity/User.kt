package org.prography.bff.user.domain.entity

import jakarta.persistence.*
import org.prography.bff.config.BaseTimeEntity
import org.prography.bff.config.exception.badrequest.InvalidRequestException
import java.util.*

@Entity
@Table(name = "users")
class User(
    provider: Provider,
    providerId: String,
    nickname: String,
) : BaseTimeEntity() {
    @Id
    val id: UUID = UUID.randomUUID()

    @Enumerated(EnumType.STRING)
    val provider: Provider = provider

    val providerId: String = providerId

    var nickname: String = nickname
        protected set

    var level: Int = 0

    var status: Boolean = false
        protected set

    fun withdraw() {
        status = true
        // 개인정보 마스킹도 여기에 함께 처리 가능
    }

    fun reactivate() {
        status = false
    }

    fun validateActive() {
        if (status) {
            throw InvalidRequestException.WithDrawUserException()
        }
    }

    fun changeNickName(newName: String) {
        nickname = newName
    }
}
