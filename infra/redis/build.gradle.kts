dependencies{

    api("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation(project(":infra:test-redis"))
}