package com.kakaopay.extract.payment.client

import com.kakaopay.extract.payment.response.PaymentApiResponseDto
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
class PaymentApiClientConfig {
    @Bean
    fun paymentApiClient(): PaymentApiClient = PaymentApiClient()
}

class PaymentApiClient(private val httpClientEngine: HttpClientEngine = CIO.create()) {
    fun getApi(userId: Long): List<PaymentApiResponseDto> = runBlocking {
        HttpClient(httpClientEngine){
            install(ContentNegotiation){
                jackson()
            }
        }.get("http://localhost:8080/payment/user/$userId")
            .body()
    }
}
