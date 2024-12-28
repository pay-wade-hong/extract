package com.kakaopay.extract.point.client

import com.kakaopay.extract.point.response.PointApiResponseDto
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
class PointApiClientConfig {
    @Bean
    fun pointApiClient(): PointApiClient = PointApiClient()
}

class PointApiClient(private val httpClientEngine: HttpClientEngine = CIO.create()) {
    fun getApi(userId: Long): List<PointApiResponseDto> = runBlocking {
        HttpClient(httpClientEngine){
            install(ContentNegotiation){
                jackson()
            }
        }.get("http://localhost:9090/point/user/$userId")
            .body()
    }
}
