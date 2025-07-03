package org.prography.bff.user.domain.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.prography.bff.user.domain.repository.UserRepository
import java.time.LocalDateTime

class UserServiceTest {
    @Test
    fun `신규 유저일 경우 isNewUser가 true여야 한다`() {
        // given
        val userRepository = mock(UserRepository::class.java)

        val service = UserService(userRepository)

        val provider = Provider.KAKAO
        val providerId = "123"
        val nickname = "테스트닉네임"
        val now = LocalDateTime.now()

        `when`(userRepository.findByProviderAndProviderId(provider, providerId))
            .thenReturn(null)

        val newUser =
            User(provider, providerId, nickname).apply {
                createdAt = now
                updatedAt = now
            }

        `when`(userRepository.save(any())).thenReturn(newUser)

        // when
        val result = service.registerIfNotExists(provider, providerId)

        // then
        assertTrue(result.isNewUser)
    }
}
