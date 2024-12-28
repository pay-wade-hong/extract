package com.kakaopay.extract.account.function

import com.kakaopay.extract.account.client.AccountApiClient
import com.kakaopay.output.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component
import java.util.function.Function

@Component
class AccountResultConvertFunction(
    private val accountApiClient: AccountApiClient
) : Function<ExtractResponseMessage, ExtractResponseMessage> {
    private val log: Logger by lazy { LoggerFactory.getLogger(javaClass) }

    override fun apply(extractResponseMessage: ExtractResponseMessage): ExtractResponseMessage {
        log.info("account extraction result: $extractResponseMessage")

        val map = extractResponseMessage.extractionContent.map {
            val accountResponseDto = accountApiClient.getApi(it.userId)
            when (it) {
                is PaymentContent -> it.copy(userName = accountResponseDto.userName)
                is PointContent -> it.copy(userName = accountResponseDto.userName)
                is HadoopContent -> it.copy(userName = accountResponseDto.userName)
            }
        }
        return extractResponseMessage.copy(extractionContent = map)
    }
}

