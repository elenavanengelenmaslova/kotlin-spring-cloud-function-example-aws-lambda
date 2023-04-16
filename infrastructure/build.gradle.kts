
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.69.0")
    implementation("software.constructs:constructs:10.1.280")
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
