package lxqtpr.ecommerce.linda.auth.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest

import lombok.extern.slf4j.Slf4j
import lxqtpr.ecommerce.linda.auth.jwt.models.JwtCookiePair
import lxqtpr.ecommerce.linda.auth.jwt.models.JwtTokenPair
import lxqtpr.ecommerce.linda.auth.jwt.models.TokenTypeEnum
import lxqtpr.ecommerce.linda.entities.UserEntity.models.UserEntity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
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
    @Value("\${security.jwt.accessExpirationInMinutes}") val accessExpiration: Long,
    @Value("\${security.jwt.refreshExpirationInMinutes}") val refreshExpiration: Long,
    val jwtRepository: JwtRepository
) {
    val accessSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret))
    val refreshSecret: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret))
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun generateAccessToken(user: UserEntity): String {
        val now: LocalDateTime = LocalDateTime.now()
        val accessExpirationInstant: Instant =
            now.plusMinutes(accessExpiration).atZone(ZoneId.systemDefault()).toInstant()
        val accessExpiration: Date = Date.from(accessExpirationInstant)
        return Jwts.builder()
            .setSubject(user.email)
            .setExpiration(accessExpiration)
            .signWith(accessSecret)
            .claim("roles", user.roles.map { it.role })
            .claim("email", user.email)
            .compact()
    }

    fun generateRefreshToken(user: UserEntity): String {
        val now: LocalDateTime = LocalDateTime.now()
        val refreshExpirationInstant: Instant = now.plusMinutes(refreshExpiration).atZone(ZoneId.systemDefault())
            .toInstant()
        val refreshExpiration: Date = Date.from(refreshExpirationInstant)
        return Jwts.builder()
            .setSubject(user.email)
            .setExpiration(refreshExpiration)
            .signWith(refreshSecret)
            .compact()
    }

    fun generateTokenPair(user: UserEntity): JwtTokenPair {
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)
        return JwtTokenPair(accessToken, refreshToken)
    }

    private fun validateToken(token: String, secret: Key): Boolean {
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
                .maxAge(Duration.ofMinutes(refreshExpiration))
                .build()

            TokenTypeEnum.ACCESS -> ResponseCookie.from(TokenTypeEnum.ACCESS.type, token)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofMinutes(accessExpiration))
                .build()
        }
    }

    fun getLogoutCookie(): JwtCookiePair {
        val accessCookieRemove = ResponseCookie.from(TokenTypeEnum.ACCESS.type, "")
            .httpOnly(true)
            .path("/")
            .maxAge(0)
            .build()
            .toString()
        val refreshCookieRemove = ResponseCookie.from(TokenTypeEnum.REFRESH.type, "")
            .httpOnly(true)
            .path("/")
            .maxAge(0)
            .build()
            .toString()
        return JwtCookiePair(accessCookieRemove, refreshCookieRemove)
    }

    fun generateCookiePair(tokenPair: JwtTokenPair): JwtCookiePair {
        val accessCookie = generateTokenCookie(TokenTypeEnum.ACCESS, tokenPair.accessToken).toString()
        val refreshCookie = generateTokenCookie(TokenTypeEnum.REFRESH, tokenPair.refreshToken).toString()
        return JwtCookiePair(accessCookie, refreshCookie)
    }
    fun getJwtFromCookies(request: HttpServletRequest, cookieName: String): String? =
        WebUtils.getCookie(request, cookieName)?.value


    fun validateAccessToken(accessToken: String): Boolean = validateToken(accessToken, accessSecret)


    fun validateRefreshToken(refreshToken: String): Boolean {
        val userEmail = getUserEmailFromClaims(refreshToken, TokenTypeEnum.REFRESH)
        return validateToken(refreshToken, refreshSecret) && jwtRepository.isRefreshTokenExist(userEmail, refreshToken)
    }


    fun getUserEmailFromClaims(refreshToken: String, tokenType: TokenTypeEnum): String {
        return when (tokenType) {
            TokenTypeEnum.ACCESS -> getClaims(refreshToken, accessSecret).subject
            TokenTypeEnum.REFRESH -> getClaims(refreshToken, refreshSecret).subject
        }
    }

    private fun getClaims(token: String, secret: Key): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
    }
}