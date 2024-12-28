package com.kakaopay.extract

import com.kakaopay.extract.account.client.AccountApiClientTestConfig
import com.kakaopay.extract.payment.client.PaymentApiClientTestConfig
import com.kakaopay.extract.point.client.PointApiClientTestConfig
import com.kakaopay.infra.redis.repository.ExtractResultHashRepository
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.test.TestRedisContainer
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.cloud.stream.binder.test.EnableTestBinder
import org.springframework.cloud.stream.binder.test.InputDestination
import org.springframework.context.annotation.Import
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.TestConstructor
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Duration
import java.util.concurrent.TimeUnit

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableTestBinder
@Testcontainers
@ImportTestcontainers(TestRedisContainer::class)
@Import(PointApiClientTestConfig::class, PaymentApiClientTestConfig::class, AccountApiClientTestConfig::class)
internal class ExtractIntegrationTest(
    private val inputDestination: InputDestination,
    private val resultHashRepository: ExtractResultHashRepository
) {
    @AfterEach
    fun deleteAll() = resultHashRepository.deleteAll()

    @Test
    fun extractFlowTest() {
        inputDestination.send(MessageBuilder.withPayload(ExtractRequestMessage(1, 1)).build(), "extract.v1")
        Awaitility.await().pollInterval(Duration.ofSeconds(3)).atMost(10, TimeUnit.SECONDS).untilAsserted {
            assertThat(resultHashRepository.findAll()).hasSize(3)

        }
    }

}