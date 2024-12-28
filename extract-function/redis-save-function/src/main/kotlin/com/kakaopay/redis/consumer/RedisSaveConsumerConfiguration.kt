package com.kakaopay.redis.consumer

import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.redis.service.ExtractResultHashService
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class RedisSaveConsumerConfiguration(
    private val extractResultHashService: ExtractResultHashService
) {
    @Bean
    fun redisSaveConsumer(): Consumer<ExtractResponseMessage> {
        return Consumer { response ->
            extractResultHashService.save(response)
        }
    }
}

