package com.example.infra

import software.amazon.awscdk.*
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.amazon.awscdk.services.logs.RetentionDays
import software.constructs.Construct

class InfrastructureJvmX86Stack(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val productsTable = Table.fromTableArn(this, "dynamoTable", Fn.importValue("Products-Spring-Cloud-Function-ExampleTableArn"))
        val functionId = "lambdaSpringCloudFunctionJvmX86"
        val function = Function.Builder.create(this, functionId)
            .description("Kotlin Lambda Spring Cloud Function JVM X86")
            .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker")
            .runtime(Runtime.JAVA_11)
            .code(Code.fromAsset("../build/dist/function.jar"))
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

        productsTable.grantReadData(function)

        CfnOutput(
            this, "${functionId}-fn-arn",
            CfnOutputProps.builder()
                .value(function.functionArn)
                .description("The arn of the $functionId function")
                .exportName("${functionId}FnArn").build()
        )
    }
}