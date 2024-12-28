package com.kakaopay.infra.redis.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(basePackages = ["com.kakaopay.infra.redis.repository"])
class RedisConfig