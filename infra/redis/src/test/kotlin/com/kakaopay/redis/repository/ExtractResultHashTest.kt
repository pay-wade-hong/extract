package com.kakaopay.redis.repository

import com.kakaopay.infra.redis.repository.ExtractResultHash
import com.kakaopay.infra.redis.repository.ExtractResultHashRepository
import com.kakaopay.test.TestRedisContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.context.ImportTestcontainers
import org.springframework.test.context.TestConstructor
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Testcontainers
@ImportTestcontainers(TestRedisContainer::class)
class ExtractResultHashTest(private val extractResultHashRepository: ExtractResultHashRepository) {

    @AfterEach
    fun deleteAll() = extractResultHashRepository.deleteAll()

    @Test
    fun saveAndFindTest() {
        extractResultHashRepository.save(ExtractResultHash(extractRequestId = 1))
        assertThat(extractResultHashRepository.findAll()).hasSize(1)
    }


}