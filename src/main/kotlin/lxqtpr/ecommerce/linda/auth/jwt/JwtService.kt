package lxqtpr.ecommerce.linda.auth.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import lombok.NonNull
import lombok.extern.slf4j.Slf4j
import lxqtpr.ecommerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.security.Key
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey


@Slf4j
@Component
class JwtService(
    @Value("\${security.jwt.accessSecret}") val jwtAccessSecret: String,
    @Value("\${security.jwt.refreshSecret}") val jwtRefreshSecret: String,
    @Value("\${security.jwt.accessExpiration}") val accessExpiration: Long,
    @Value("\${security.jwt.refreshExpiration}") val refreshExpiration: Long,
    @Value("\${security.jwt.header}") val authHeader: String,
    val userDetailsService: UserDetailsService
) {
    val accessSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret))
    val refreshSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret))
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun generateAccessToken(@NonNull user: UserEntity): String {
        val now: LocalDateTime = LocalDateTime.now()
        val accessExpirationInstant: Instant =
            now.plusSeconds(accessExpiration).atZone(ZoneId.systemDefault()).toInstant()
        val accessExpiration: Date = Date.from(accessExpirationInstant)
        return Jwts.builder()
            .setSubject(user.email)
            .setExpiration(accessExpiration)
            .signWith(accessSecret)
            .claim("roles", user.roles.map { it.role })
            .claim("email", user.email)
            .compact()
    }

    fun generateRefreshToken(@NonNull user: UserEntity): String {
        val now: LocalDateTime = LocalDateTime.now()
        val refreshExpirationInstant:Instant = now.plusSeconds(refreshExpiration).atZone(ZoneId.systemDefault())
            .toInstant()
        val refreshExpiration: Date = Date.from(refreshExpirationInstant)
        return Jwts.builder()
            .setSubject(user.email)
            .setExpiration(refreshExpiration)
            .signWith(refreshSecret)
            .compact()
    }
    fun generateTokenPair(@NonNull user: UserEntity): JwtResponse {
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)
        return JwtResponse(accessToken, refreshToken)
    }

    private fun validateToken(@NonNull token: String, @NonNull secret: Key): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (expEx : ExpiredJwtException) {
            log.error("Token expired", expEx)
        } catch (unsEx : UnsupportedJwtException) {
            log.error("Unsupported jwt")
        } catch (mjEx: MalformedJwtException ) {
            log.error("Malformed jwt")
        } catch ( e: Exception) {
            log.error("invalid token", e)
        }
        return false
    }

    fun generateTokenCookie(tokenType: TokenTypeEnum, token: String): ResponseCookie {
        return when (tokenType) {
            TokenTypeEnum.REFRESH -> ResponseCookie.from(TokenTypeEnum.REFRESH.type, token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofSeconds(refreshExpiration))
                .build()

            TokenTypeEnum.ACCESS -> ResponseCookie.from(TokenTypeEnum.ACCESS.type, token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofSeconds(accessExpiration))
                .build()
        }
    }

    fun getJwtFromCookies(request: HttpServletRequest, cookieName: String): String? =
        WebUtils.getCookie(request, cookieName)?.value


    fun validateAccessToken(accessToken: String): Boolean {
        return validateToken(accessToken, accessSecret)
    }

    fun validateRefreshToken(@NonNull refreshToken: String): Boolean {
        return validateToken(refreshToken, refreshSecret)
    }

    fun getUserEmailFromAccessClaims(@NonNull token: String): String {
        return getClaims(token, accessSecret).subject
    }

    fun getRefreshClaims(@NonNull token: String): Claims? {
        return getClaims(token, refreshSecret)
    }


    private fun getClaims(@NonNull token: String, @NonNull secret: Key): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    }
}