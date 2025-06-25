package org.prography.bff.config.security

import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.prography.bff.auth.domain.model.TokenType
import org.prography.bff.user.domain.entity.Provider
import org.prography.bff.user.domain.entity.User
import org.prography.bff.user.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class AuthFilterTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val jwtProvider: JwtProvider,
    @Autowired val userRepository: UserRepository,
) {
    private lateinit var accessToken: String

    @BeforeEach
    fun setup() {
        val user =
            userRepository.save(
                User(
                    provider = Provider.KAKAO,
                    providerId = "test",
                    nickname = "돼지고기1234",
                ),
            )

        accessToken =
            jwtProvider.createToken(
                tokenType = TokenType.ACCESS_TOKEN,
                subject = user.id.toString(),
            )
    }

    @Test
    fun `valid token passes authentication and authorization`() {
        mockMvc.perform(
            get("/users/me")
                .header("Authorization", "Bearer $accessToken"),
        )
            .andExpect(status().isOk)
            .andExpect(content().string(Matchers.containsString("돼지고기1234")))
    }

    @Test
    fun `missing token returns unauthorized`() {
        mockMvc.perform(get("/users/me"))
            .andExpect(status().isForbidden)
            .andExpect(content().string(Matchers.containsString("접근 권한이 없는 유저입니다.")))
    }

    @Test
    fun `invalid token returns unauthorized`() {
        mockMvc.perform(
            get("/users/me")
                .header("Authorization", "Bearer invalid.token.here"),
        )
            .andExpect(status().isUnauthorized)
    }
}
