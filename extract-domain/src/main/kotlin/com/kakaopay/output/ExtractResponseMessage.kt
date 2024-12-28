package com.kakaopay.output

import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.math.BigDecimal

data class ExtractResponseMessage(
    val extractRequestId: Long,
    val extractionStatus: ExtractionStatus = ExtractionStatus.NONE,
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    val extractionContent: List<ExtractionContent> = listOf()
)

enum class ExtractionStatus {
    NONE, SUCCESS, SUCCESS_WITHOUT_RESULT, FAIL, NOT_VALID
}

sealed interface ExtractionContent {
    val userId: Long
    val userName: String?
}

data class PaymentContent(
    override val userId: Long,
    override val userName: String? = null,
    val tractionType: String,
    val transactionAmount: BigDecimal,
) : ExtractionContent

data class PointContent(
    override val userId: Long,
    override val userName: String? = null,
    val pointAmount: BigDecimal,
) : ExtractionContent

data class HadoopContent(
    override val userId: Long,
    override val userName: String? = null,
    val etcInfo: String,
) : ExtractionContent