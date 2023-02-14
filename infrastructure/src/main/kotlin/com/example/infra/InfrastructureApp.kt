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

    val stackNameTable = "Kotlin-Lambda-Spring-Cloud-Function-table"
    InfrastructureTableStack(
        app, stackNameTable, StackProps.builder()
            .stackName(stackNameTable)
            .env(environment)
            .description("Dynamo Table used for Spring-Cloud-Function example")
            .build()
    )

    val stackNameJvmX86 = "Kotlin-Lambda-Spring-Cloud-Function-Jvm-X86"
    InfrastructureJvmX86Stack(
        app, stackNameJvmX86,
        StackProps.builder()
            .stackName(stackNameJvmX86)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM X86")
            .build()
    )

    val stackNameJvmArm64 = "Kotlin-Lambda-Spring-Cloud-Function-Jvm-Arm64"
    InfrastructureJvmArm64Stack(
        app, stackNameJvmArm64,
        StackProps.builder()
            .stackName(stackNameJvmArm64)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM ARM64")
            .build()
    )

    val stackNameJvmX86C1 = "Kotlin-Lambda-Spring-Cloud-Function-Jvm-X86-C1"
    InfrastructureJvmX86C1Stack(
        app, stackNameJvmX86C1,
        StackProps.builder()
            .stackName(stackNameJvmX86C1)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM X86 C1")
            .build()
    )

    val stackNameJvmArm64C1 = "Kotlin-Lambda-Spring-Cloud-Function-Jvm-Arm64-C1"
    InfrastructureJvmArm64C1Stack(
        app, stackNameJvmArm64C1,
        StackProps.builder()
            .stackName(stackNameJvmArm64C1)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM ARM64 C1")
            .build()
    )

    //SnapStart only
    val stackNameSnapStartOnly = "Kotlin-Lambda-Spring-Cloud-Function-SnapStart-Only"
    InfrastructureSnapStartOnlyStack(
        app, stackNameSnapStartOnly,
        StackProps.builder()
            .stackName(stackNameSnapStartOnly)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM X86 SnapStart Only")
            .build()
    )

    //SnapStart C1
    val stackNameSnapStartC1 = "Kotlin-Lambda-Spring-Cloud-Function-SnapStart-C1"
    InfrastructureSnapStartC1Stack(
        app, stackNameSnapStartC1,
        StackProps.builder()
            .stackName(stackNameSnapStartC1)
            .env(environment)
            .description("Kotlin Spring Cloud Function JVM X86 SnapStart C1")
            .build()
    )
    //SnapStart priming
    //SnapStart priming C1
    //Native X86
    //Native ARM 64
    app.synth()
}
