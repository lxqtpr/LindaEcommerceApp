package lxqtpr.eccomerce.linda.auth.jwt


import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class JwtRepository(
        private val redisTemplate: RedisTemplate<String, String>
) {
     fun setRefreshToken(userEmail: String, token: String) {
        redisTemplate.opsForValue().set(userEmail, token)
        redisTemplate.expire(userEmail, 50000, TimeUnit.SECONDS)
    }
    fun removeRefreshToken(userEmail: String){
        redisTemplate.delete(userEmail)
    }
}