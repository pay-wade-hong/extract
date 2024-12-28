package com.kakaopay.extract.account.client

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class AccountApiClientTestConfig {
    @Bean
    @Primary
    fun mockAccountApiClient(): AccountApiClient {
        val mockEngine = MockEngine {
            return@MockEngine respond(
                content = """
                   {"userName": "userName"}
                   
                """.trimIndent(),
                headers = headersOf(HttpHeaders.ContentType, listOf(ContentType.Application.Json.toString()))
            )
        }
        return AccountApiClient(mockEngine)
    }
}