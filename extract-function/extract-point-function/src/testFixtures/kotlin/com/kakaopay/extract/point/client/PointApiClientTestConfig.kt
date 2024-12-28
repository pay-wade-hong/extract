package com.kakaopay.extract.point.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class PointApiClientTestConfig {
    @Bean
    @Primary
    fun mockPointApiClient(): PointApiClient {
        val mockEngine = MockEngine {
            return@MockEngine respond(
                content = """
                    [
                      {
                      "userId": 1, 
                      "pointAmount": 1000
                      }
                    ]
                   
                """.trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, listOf(ContentType.Application.Json.toString()))
            )
        }
        return PointApiClient(mockEngine)
    }
}