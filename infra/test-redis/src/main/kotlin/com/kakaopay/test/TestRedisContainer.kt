package com.kakaopay.test

import com.redis.testcontainers.RedisContainer
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

class TestRedisContainer {

    companion object {

        @Container
        val redisContainer: RedisContainer = RedisContainer(
            DockerImageName.parse("redis:6.2.13"),
        )

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { redisContainer.host }
            registry.add("spring.data.redis.port") {
                redisContainer.firstMappedPort
            }
        }

    }
}