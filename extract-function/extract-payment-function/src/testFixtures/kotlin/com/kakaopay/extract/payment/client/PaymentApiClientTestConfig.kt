package com.kakaopay.extract.payment.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class PaymentApiClientTestConfig {
    @Bean
    @Primary
    fun mockPaymentApiClient(): PaymentApiClient {
        val mockEngine = MockEngine {
            return@MockEngine respond(
                content = """
                    [
                      {
                      "id": 1, 
                      "paymentType": "CARD",
                      "paymentAmount": 10000
                      }
                    ]
                   
                """.trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, listOf(ContentType.Application.Json.toString()))
            )
        }
        return PaymentApiClient(mockEngine)
    }
}