package com.regisoc.shared.infrastructure.security

import com.regisoc.modules.users.domain.User
import com.regisoc.modules.users.domain.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.expiration}") private val expirationMs: Long
) {

    private val key: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(user: User): String {
        val now = System.currentTimeMillis()
        val claims = mutableMapOf<String, Any>(
            "username" to user.username,
            "role" to user.role.name
        )
        if (user.club != null) {
            claims["clubId"] = user.club!!.id
        }
        return Jwts.builder()
            .claims(claims)
            .subject(user.username)
            .issuedAt(Date(now))
            .expiration(Date(now + expirationMs))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String = extractClaim(token) { it.subject }

    fun extractRole(token: String): UserRole = extractClaim(token) {
        UserRole.valueOf(it["role", String::class.java])
    }

    fun extractClubId(token: String): Long? {
        val claims = extractAllClaims(token)
        val clubId = claims["clubId"]
        return (clubId as? Number)?.toLong()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun getRemainingExpirationMs(token: String): Long {
        val expiration = extractClaim(token) { it.expiration }
        return expiration.time - System.currentTimeMillis()
    }

    fun isTokenExpired(token: String): Boolean {
        return extractClaim(token) { it.expiration }.before(Date())
    }

    private fun <T> extractClaim(token: String, resolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return resolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}
