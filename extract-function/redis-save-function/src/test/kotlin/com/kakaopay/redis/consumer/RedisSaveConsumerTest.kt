package com.kakaopay.redis.consumer

import com.kakaopay.infra.redis.repository.ExtractResultHashRepository
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.test.TestRedisContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.test.context.TestConstructor
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.function.Consumer

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Testcontainers
@ImportTestcontainers(TestRedisContainer::class)
class RedisSaveConsumerTest(
    private val redisSaveConsumer: Consumer<ExtractResponseMessage>,
    private val resultHashRepository: ExtractResultHashRepository
) {
    @AfterEach
    fun deleteAll() = resultHashRepository.deleteAll()

    @Test
    fun redisSaveConsumerTest() {
        redisSaveConsumer.accept(ExtractResponseMessage(1, ExtractionStatus.SUCCESS, listOf()))
        assertThat(resultHashRepository.findAll()).hasSize(1)
    }

}