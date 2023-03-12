plugins {
    kotlin("plugin.spring") version "1.8.10"
    id("org.springframework.boot") version "2.7.8"
    id("io.spring.dependency-management") version "1.1.0"
}
buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.7.8")
    }
}

dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.18.41")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.2")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-function-adapter-aws
    implementation("org.springframework.cloud:spring-cloud-function-adapter-aws:3.2.8")
    // https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-function-web
    implementation("org.springframework.cloud:spring-cloud-function-web:3.2.8")
    implementation("io.github.crac:org-crac:0.1.3")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.5.31")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.8.0")
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

tasks.bootJar {
    from(tasks.compileKotlin)
    from(tasks.compileJava)
    from(tasks.processResources)
    into("lib") {
        from(configurations.runtimeClasspath)
    }
    archiveFileName.set("function-with-priming.jar")
    destinationDirectory.set(file("${project.rootDir}/build/dist"))
}