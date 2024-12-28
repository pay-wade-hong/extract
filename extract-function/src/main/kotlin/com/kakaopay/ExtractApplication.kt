package com.kakaopay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ExtractApplication

fun main(vararg args: String) {
    runApplication<ExtractApplication>(*args)
}
