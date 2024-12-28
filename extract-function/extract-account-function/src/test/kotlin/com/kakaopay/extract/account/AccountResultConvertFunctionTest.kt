package com.kakaopay.extract.account

import com.kakaopay.extract.account.client.AccountApiClientTestConfig
import com.kakaopay.extract.account.function.AccountResultConvertFunction
import com.kakaopay.output.ExtractResponseMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.PaymentContent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(AccountApiClientTestConfig::class)
class AccountResultConvertFunctionTest(private val function: AccountResultConvertFunction) {

    @Test
    fun paymentFunctionSuccessTest() {
        val extractResponseMessage = function.apply(ExtractResponseMessage(1, ExtractionStatus.SUCCESS, listOf(
            PaymentContent(
                userId = 1, tractionType =  "CARD", transactionAmount = 100.toBigDecimal())

        )))
        assertThat(extractResponseMessage.extractionStatus).isEqualTo(ExtractionStatus.SUCCESS)
        assertThat(extractResponseMessage.extractionContent).hasSize(1)
        assertThat((extractResponseMessage.extractionContent[0] as PaymentContent).tractionType).isEqualTo("CARD")
        assertThat((extractResponseMessage.extractionContent[0] as PaymentContent).userName).isEqualTo("userName")
    }

}