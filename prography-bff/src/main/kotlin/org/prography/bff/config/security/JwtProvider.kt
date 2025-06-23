package org.prography.bff.config.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.prography.bff.auth.domain.model.TokenType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.access-expiration}") private val accessExpirationMs: Long,
    @Value("\${jwt.refresh-expiration}") private val refreshExpirationMs: Long,
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createToken(
        tokenType: TokenType,
        subject: String,
        claims: Map<String, Any> = emptyMap(),
    ): String {
        val now = Date()
        val expiry =
            when (tokenType) {
                TokenType.ACCESS_TOKEN -> Date(now.time + accessExpirationMs)
                TokenType.REFRESH_TOKEN -> Date(now.time + refreshExpirationMs)
            }

        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .addClaims(claims)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        }

    fun getUserId(token: String): UUID {
        val claims =
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        return UUID.fromString(claims.subject)
    }
}
