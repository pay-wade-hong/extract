plugins {
    id("com.google.cloud.tools.jib") version "3.4.4"

}
apply(plugin = "java-test-fixtures")

extra["springCloudVersion"] = "2023.0.4"

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")
    testImplementation("org.testcontainers:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(project(":extract-domain"))
    implementation(project(":extract-function:extract-payment-function"))
    implementation(project(":extract-function:extract-point-function"))
    implementation(project(":extract-function:extract-account-function"))
    implementation(project(":extract-function:extract-hadoop-function"))
    implementation(project(":extract-function:redis-save-function"))

    implementation(project(":infra:redis"))

    testImplementation(testFixtures(project(":extract-function:extract-payment-function")))
    testImplementation(testFixtures(project(":extract-function:extract-point-function")))
    testImplementation(testFixtures(project(":extract-function:extract-account-function")))
    testImplementation(project(":infra:test-redis"))

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
subprojects {
    apply(plugin = "java-test-fixtures")
    tasks.jar {
        enabled = true
    }
    tasks.bootJar {
        enabled  = false
    }
    dependencies {
        implementation(project(":extract-domain"))
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

}

tasks.jar {
    enabled = true
}
tasks.bootJar {
    enabled = true
}

jib{
    dockerClient.executable ="/usr/local/bin/docker"
    from {
        image = "eclipse-temurin:21-jre"
    }
    to {
        image = "extract"
    }
}