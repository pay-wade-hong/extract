package com.kakaopay.infra.redis.repository

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.math.BigDecimal

@RedisHash
data class ExtractResultHash(
    @Id
    var id :String? = null,

    @Indexed
    val extractRequestId: Long,

    val extractionResult: List<ExtractionContentHash> = listOf()
)

sealed interface ExtractionContentHash {
    val userId: Long
    val userName: String?
}

data class PaymentContentHash(
    override val userId: Long,
    override val userName: String? = null,
    val tractionType: String,
    val transactionAmount: BigDecimal,
) : ExtractionContentHash

data class PointContentHash(
    override val userId: Long,
    override val userName: String? = null,
    val pointAmount: BigDecimal,
) : ExtractionContentHash

data class HadoopContentHash(
    override val userId: Long,
    override val userName: String? = null,
    val etcInfo: String,
) : ExtractionContentHash