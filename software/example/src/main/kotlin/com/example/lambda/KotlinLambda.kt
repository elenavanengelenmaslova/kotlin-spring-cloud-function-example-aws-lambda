package com.example.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

@Suppress("UNUSED")
class KotlinLambda : RequestHandler<Map<Any, Any>, String> {

    override fun handleRequest(event: Map<Any, Any>, context: Context): String {
        return "Hello world!"
    }
}