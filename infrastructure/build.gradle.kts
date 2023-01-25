
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.55.1")
    implementation("software.constructs:constructs:10.1.194")
}

application {
    mainClass.set("com.example.infra.InfrastructureAppKt")
}

tasks.named("run") {
    dependsOn(":example:packageDistribution")
}
repositories {
    mavenCentral()
}
