package com.kakaopay.extract.account.client

import com.kakaopay.extract.account.response.AccountResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.jackson.*
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccountApiClientConfig {
    @Bean
    fun accountApiClient(): AccountApiClient = AccountApiClient()
}

class AccountApiClient(private val httpClientEngine: HttpClientEngine = CIO.create()) {
    fun getApi(userId: Long): AccountResponseDto = runBlocking {
        HttpClient(httpClientEngine){
            install(ContentNegotiation){
                jackson()
            }
        }.get("http://localhost:8080/account/user/$userId")
            .body()
    }
}
