plugins {
    kotlin("plugin.spring")
    id("org.springframework.boot") version "3.1.1"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
apply(plugin = "io.spring.dependency-management")

dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.20.68")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.1")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-function-adapter-aws
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:4.0.4")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.5.31")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
}

configurations {
    runtimeClasspath {
        exclude("org.apache.httpcomponents")
        exclude("org.jetbrains")
    }
}
repositories {
    mavenCentral()
}
tasks.shadowJar {
    archiveFileName.set("function-native.jar")
    destinationDirectory.set(file("${project.rootDir}/build/dist"))
    mergeServiceFiles()
    append("META-INF/spring.handlers")
    append("META-INF/spring.schemas")
    append("META-INF/spring.tooling")
    append("META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports")
    append("META-INF/spring/org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration.imports")
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.PropertiesFileTransformer().apply {
        paths = listOf("META-INF/spring.factories")
        mergeStrategy = "append"
    })
    manifest {
        attributes(mapOf("Main-Class" to "com.example.lambda.Application"))
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}