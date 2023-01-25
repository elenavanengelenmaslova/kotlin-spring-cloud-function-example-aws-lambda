package com.example.lambda

import com.amazonaws.services.lambda.runtime.Context
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.kotlin.mock

internal class KotlinLambdaTest {

    private val context: Context = mock()

    @Test
    fun handleRequest() {
        assertEquals("Hello world!", KotlinLambda().handleRequest(emptyMap(), context))
    }
}