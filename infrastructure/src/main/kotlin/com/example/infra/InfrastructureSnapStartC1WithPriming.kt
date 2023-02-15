package com.example.infra

import software.amazon.awscdk.*
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.lambda.CfnFunction
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.amazon.awscdk.services.logs.RetentionDays
import software.constructs.Construct

class InfrastructureSnapStartC1WithPriming(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val productsTable = Table.fromTableArn(this, "dynamoTable", Fn.importValue("Products-Spring-Cloud-Function-ExampleTableArn"))
        val functionId = "lambdaSpringCloudFunctionSnapStartC1WithPriming"
        val function = Function.Builder.create(this, functionId)
            .description("Kotlin Lambda Spring Cloud Function JVM X86 SnapStart C1 With Priming")
            .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker")
            .runtime(Runtime.JAVA_11)
            .code(Code.fromAsset("../build/dist/function-with-priming.jar"))
            .logRetention(RetentionDays.ONE_WEEK)
            .memorySize(512)
            .environment(
                mapOf(
                    "SPRING_CLOUD_FUNCTION_DEFINITION" to "handleRequest",
                    "MAIN_CLASS" to "com.example.lambda.Application",
                    // Ensure lambda version is updated with latest lambda code
                    "CodeVersionString" to System.getenv("BUILD_NO")
                )
            )
            .timeout(Duration.seconds(120))
            .build()

        //enable SnapStart
        (function.node.defaultChild as CfnFunction).setSnapStart(
            CfnFunction.SnapStartProperty.builder()
                .applyOn("PublishedVersions")
                .build()
        )
        // publish a version
        function.currentVersion

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