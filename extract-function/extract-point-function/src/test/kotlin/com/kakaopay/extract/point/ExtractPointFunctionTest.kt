package com.kakaopay.extract.point

import com.kakaopay.extract.point.client.PointApiClientTestConfig
import com.kakaopay.extract.point.function.ExtractPointFunction
import com.kakaopay.input.ExtractRequestMessage
import com.kakaopay.output.ExtractionStatus
import com.kakaopay.output.PaymentContent
import com.kakaopay.output.PointContent
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Import(PointApiClientTestConfig::class)
class ExtractPointFunctionTest(private val function: ExtractPointFunction) {

    @Test
    fun pointFunctionSuccessTest() {
        val extractResponseMessage = function.apply(ExtractRequestMessage(1, 1))
        assertThat(extractResponseMessage.extractionStatus).isEqualTo(ExtractionStatus.SUCCESS)
        assertThat(extractResponseMessage.extractionContent).hasSize(1)
        assertThat((extractResponseMessage.extractionContent[0] as PointContent).pointAmount).isEqualByComparingTo(1000.toBigDecimal())
    }

}