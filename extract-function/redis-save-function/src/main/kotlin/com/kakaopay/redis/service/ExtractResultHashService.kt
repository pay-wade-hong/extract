package com.kakaopay.redis.service

import com.kakaopay.infra.redis.repository.*
import com.kakaopay.output.*
import org.springframework.stereotype.Service
import java.lang.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

@Service
class ExtractResultHashService(private val resultHashRepository: ExtractResultHashRepository) {

    fun save(response: ExtractResponseMessage) {
        val extractionResults = response.extractionContent.map {
            it.toContentHashList()
        }.toList()

        val extractResultHash = ExtractResultHash(
            extractRequestId = response.extractRequestId,
            extractionResult = extractionResults
        )
        resultHashRepository.save(extractResultHash)
    }
}

private fun ExtractionContent.toContentHashList(): ExtractionContentHash {
    return when (this) {
        is HadoopContent ->
            Transformer(
                inClass = HadoopContent::class,
                outclass = HadoopContentHash::class
            ).transform(this)

        is PaymentContent -> Transformer(
            inClass = PaymentContent::class,
            outclass = PaymentContentHash::class
        ).transform(this)

        is PointContent -> Transformer(
            inClass = PointContent::class,
            outclass = PointContentHash::class
        ).transform(this)
    }
}


open class Transformer<in T : Any, out R : Any>(
    inClass: KClass<T>, outclass: KClass<R>
) {
    private val outConstructor = outclass.primaryConstructor!!
    private val inPropertiesByName: Map<String, KProperty1<T, *>> by lazy {
        inClass.memberProperties.associateBy { it.name }
    }

    fun transform(data: T): R = with(outConstructor) {
        callBy(parameters.associateWith { parameter -> argFor(parameter, data) })
    }

    open fun argFor(parameter: KParameter, data: T): Any? {
        return inPropertiesByName[parameter.name]?.get(data)
    }
}
