package com.example.lambda

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message

@SpringBootApplication
class KotlinLambda {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val gson = Gson()

    @Bean
    fun handleRequest(): (Message<Any>) -> String {
        return {
            logger.info(gson.toJson(it))
            "Hello world!"
        }
    }
}
fun main(args: Array<String>) {
    runApplication<KotlinLambda>(*args)
}
