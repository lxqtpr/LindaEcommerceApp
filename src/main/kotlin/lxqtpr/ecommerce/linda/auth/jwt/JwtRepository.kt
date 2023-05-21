package lxqtpr.ecommerce.linda.auth.jwt


import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class JwtRepository(
    @Value("\${security.jwt.refreshExpirationInMinutes}") val refreshExpiration: Long,
    val redisTemplate: RedisTemplate<String, String>
) {
     fun setRefreshToken(userEmail: String, token: String) {
        redisTemplate.opsForValue().set(userEmail, token)
         redisTemplate.expire(userEmail, refreshExpiration, TimeUnit.MINUTES)
    }
    fun removeRefreshToken(userEmail: String){
        redisTemplate.delete(userEmail)
    }

    fun isRefreshTokenExist(userEmail: String, refreshToken: String): Boolean =
        redisTemplate.opsForValue().get(userEmail) == refreshToken
}