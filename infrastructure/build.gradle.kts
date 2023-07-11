
plugins {
    application
}

dependencies {
    // https://mvnrepository.com/artifact/software.amazon.awscdk/aws-cdk-lib
    implementation("software.amazon.awscdk:aws-cdk-lib:2.87.0")
    // https://mvnrepository.com/artifact/software.constructs/constructs
    implementation("software.constructs:constructs:10.2.69")
}

application {
    mainClass.set("com.example.infra.InfrastructureAppKt")
}

tasks.named("run") {
    dependsOn(":example:build")
}
repositories {
    mavenCentral()
}
