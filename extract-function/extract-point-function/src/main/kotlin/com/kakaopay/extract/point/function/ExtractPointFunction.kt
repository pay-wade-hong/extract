package com.kakaopay.extract.point.function

import com.kakaopay.extract.point.client.PointApiClient
import com.kakaopay.extract.point.response.PointApiResponseDto
import com.kakaopay.function.ExtractFunction
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.PaymentContent
import com.kakaopay.output.PointContent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExtractPointFunction(
    private val pointApiClient: PointApiClient
) : ExtractFunction {
    private val log: Logger by lazy { LoggerFactory.getLogger(javaClass) }

    override val requestValid: (ExtractRequestMessage) -> Boolean = { true }

    override fun doAction(request: ExtractRequestMessage): ExtractResponseMessage {
        log.info("point extraction request: $request")
        val pointApiContents = pointApiClient.getApi(request.userId)

        return ExtractResponseMessage(
            extractRequestId = request.extractRequestId,
            extractionStatus = if (pointApiContents.isNotEmpty()) ExtractionStatus.SUCCESS else ExtractionStatus.SUCCESS_WITHOUT_RESULT,
            extractionContent = pointApiContents.map { it.toExtractionContent() }
        )
    }

    private fun PointApiResponseDto.toExtractionContent() = PointContent(
        userId = userId,
         pointAmount = pointAmount.toBigDecimal()
    )

}

