package com.kakaopay.extract.hadoop.function

import com.kakaopay.function.ExtractFunction
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.HadoopContent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExtractHadoopFunction: ExtractFunction {
    private val log: Logger by lazy { LoggerFactory.getLogger(javaClass) }
    override val requestValid: (ExtractRequestMessage) -> Boolean = { true }

    override fun doAction(request: ExtractRequestMessage): ExtractResponseMessage {
        log.info("Hadoop extraction request: $request")
        return ExtractResponseMessage(
            extractRequestId = request.extractRequestId,
            extractionStatus = ExtractionStatus.SUCCESS,
            extractionContent = listOf(HadoopContent(userId = request.userId, etcInfo = "Hadoop Extraction"))
        )
    }
}

