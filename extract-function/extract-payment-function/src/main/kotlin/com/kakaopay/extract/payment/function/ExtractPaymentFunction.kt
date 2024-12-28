package com.kakaopay.extract.payment.function

import com.kakaopay.extract.payment.client.PaymentApiClient
import com.kakaopay.extract.payment.response.PaymentApiResponseDto
import com.kakaopay.function.ExtractFunction
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.PaymentContent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExtractPaymentFunction(
    private val paymentApiClient: PaymentApiClient
) : ExtractFunction {
    private val log: Logger by lazy { LoggerFactory.getLogger(javaClass) }
    override val requestValid: (ExtractRequestMessage) -> Boolean = { true }

    override fun doAction(request: ExtractRequestMessage): ExtractResponseMessage {
        log.info("payment extraction request: $request")
        val paymentApiContents = paymentApiClient.getApi(request.userId)

        return ExtractResponseMessage(
            extractRequestId = request.extractRequestId,
            extractionStatus = if (paymentApiContents.isNotEmpty()) ExtractionStatus.SUCCESS else ExtractionStatus.SUCCESS_WITHOUT_RESULT,
            extractionContent = paymentApiContents.map { it.toExtractionContent() }
        )
    }

    private fun PaymentApiResponseDto.toExtractionContent() = PaymentContent(
        userId = id,
        tractionType = paymentType,
        transactionAmount = paymentAmount.toBigDecimal()
    )

}

