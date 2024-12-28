package com.kakaopay.extract.payment

import com.kakaopay.extract.payment.client.PaymentApiClientTestConfig
import com.kakaopay.extract.hadoop.function.ExtractPaymentFunction
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.PaymentContent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(PaymentApiClientTestConfig::class)
class ExtractPaymentFunctionTest(private val function: ExtractPaymentFunction) {

    @Test
    fun paymentFunctionSuccessTest() {
        val extractResponseMessage = function.apply(ExtractRequestMessage(1, 1))
        assertThat(extractResponseMessage.extractionStatus).isEqualTo(ExtractionStatus.SUCCESS)
        assertThat(extractResponseMessage.extractionContent).hasSize(1)
        assertThat((extractResponseMessage.extractionContent[0] as PaymentContent).tractionType).isEqualTo("CARD")
    }

}