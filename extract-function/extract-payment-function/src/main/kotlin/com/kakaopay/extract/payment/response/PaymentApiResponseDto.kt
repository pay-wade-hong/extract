package com.kakaopay.extract.payment.response

data class PaymentApiResponseDto(
    val id: Long,
    val paymentAmount: Long,
    val paymentType: String
)