import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    `maven-publish`
}

group = "br.com.docencia"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()
    maven {
        name = "open-ia" // Nome do repositório privado
        url = uri("https://github.com/pitagorasampli/open-ia/tree/develop")
        credentials {
            username = project.findProperty("repoUsername") as String? ?: System.getenv("REPO_USERNAME")
            password = project.findProperty("repoPassword") as String? ?: System.getenv("REPO_PASSWORD")
        }
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.theokanning.openai-gpt3-java:api:0.12.0")
    implementation("com.theokanning.openai-gpt3-java:client:0.12.0")

    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.getByName("jar") {
    enabled = false
}
