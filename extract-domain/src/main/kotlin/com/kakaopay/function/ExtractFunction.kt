package com.kakaopay.function

import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.function.Function

interface ExtractFunction : Function<ExtractRequestMessage, ExtractResponseMessage> {
    private val log: Logger get() = LoggerFactory.getLogger(this.javaClass)

    /*
    * 추출
    Function 을 수행하기 위한 선행조건(pre-condition)을 체크한다.
    *- Function 을 수행할 충분한 선행조건이 충족되지 않을 경우 NOT_VALID 로 전송
     */
    val requestValid: (ExtractRequestMessage) -> Boolean

    // 추출 로직 정의
    fun doAction(request: ExtractRequestMessage): ExtractResponseMessage

    // 추출 진행 및 예외 처리
    override fun apply(request: ExtractRequestMessage): ExtractResponseMessage {
        return when {
            requestValid(request) -> {
                try {
                    doAction(request)
                } catch (e: Exception) {
                    log.error("error message: ${e.message}", e)
                    responseByRequestAndStatus(request, ExtractionStatus.FAIL)
                }
            }

            else -> responseByRequestAndStatus(request, ExtractionStatus.NOT_VALID)
        }
    }

    private fun responseByRequestAndStatus(request: ExtractRequestMessage, status: ExtractionStatus) =
        ExtractResponseMessage(
            extractRequestId = request.extractRequestId,
            extractionStatus = status,
        )
}