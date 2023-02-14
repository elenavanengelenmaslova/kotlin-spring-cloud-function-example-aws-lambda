package com.example.lambda

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message

@Configuration
class KotlinLambda {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val gson = Gson()

    @Bean
    fun handleRequest(): (Message<Map<String, String>>) -> String {
        return {
            logger.info(gson.toJson(it.payload))
            "Hello world!"
        }
    }
}