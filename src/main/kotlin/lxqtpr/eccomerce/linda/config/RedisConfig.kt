package lxqtpr.eccomerce.linda.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer


@Configuration
class RedisConfig() {

    @Bean
    fun connectionFactory(): RedisConnectionFactory {
        val redisConfiguration: RedisConfiguration

        redisConfiguration = RedisStandaloneConfiguration("localhost", 6379)

        return LettuceConnectionFactory(redisConfiguration)
    }
    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = connectionFactory()

        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer)

        return redisTemplate
    }
}