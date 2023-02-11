package com.example.infra

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

fun main() {
    val app = App()

    val environment = Environment.builder()
        .account(System.getenv("DEPLOY_TARGET_ACCOUNT"))
        .region(System.getenv("DEPLOY_TARGET_REGION"))
        .build()

    val stackName = "Kotlin-Lambda-Spring-Cloud-Function"
    InfrastructureJvmArm64Stack(
        app, stackName,
        StackProps.builder()
            .stackName(stackName)
            .env(environment)
            .description("Kotlin Spring Cloud Function")
            .build()
    )

    app.synth()
}
