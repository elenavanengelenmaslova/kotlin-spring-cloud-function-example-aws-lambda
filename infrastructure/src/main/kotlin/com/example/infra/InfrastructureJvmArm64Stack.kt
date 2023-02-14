package com.example.infra

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.lambda.Architecture
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.amazon.awscdk.services.logs.RetentionDays
import software.constructs.Construct

class InfrastructureJvmArm64Stack(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val functionId = "lambdaStringCloudFunctionJvmARM64"
        Function.Builder.create(this, functionId)
            .description("Kotlin Lambda Spring Cloud Function")
            .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker")
            .runtime(Runtime.JAVA_11)
            .code(Code.fromAsset("../build/dist/function.jar"))
            .architecture(Architecture.ARM_64)
            .logRetention(RetentionDays.ONE_WEEK)
            .memorySize(512)
            .environment(
                mapOf(
                    "SPRING_CLOUD_FUNCTION_DEFINITION" to "handleRequest",
                    "MAIN_CLASS" to "com.example.lambda.Application"
                )
            )
            .timeout(Duration.seconds(120))
            .build()
    }
}