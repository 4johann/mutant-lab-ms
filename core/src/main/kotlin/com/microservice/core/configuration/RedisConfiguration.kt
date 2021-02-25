package com.microservice.core.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import javax.annotation.PostConstruct

@Configuration
class RedisConfiguration {
    companion object {
        lateinit var host: String
        lateinit var port: String
    }

    @Value("\${spring.redis.host}")
    fun setRedisHost(env: String) {
        host = env
    }

    @Value("\${spring.redis.port}")
    fun setRedisPort(env: String) {
        port = env
    }

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val redisConfig = RedisStandaloneConfiguration()
        redisConfig.hostName = host
        redisConfig.port = Integer.valueOf(port)
        return JedisConnectionFactory(redisConfig)
    }

    @PostConstruct
    fun postConstruct() {
        StringRedisTemplate(jedisConnectionFactory())
    }
}
