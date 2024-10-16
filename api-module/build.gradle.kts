plugins {
    kotlin("plugin.jpa")
    kotlin("kapt")
    idea
}

val jsonwebtokenVersion: String by project
val querydslVersion: String by project
val springdocVersion: String by project
val mockkVersion: String by project

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")

    // Docs
    implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
    // Test
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:mysql")
    testImplementation("io.mockk:mockk:${mockkVersion}")

    // Security
    implementation("io.jsonwebtoken:jjwt-api:$jsonwebtokenVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jsonwebtokenVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jsonwebtokenVersion")

    // FCM
    implementation("com.google.firebase:firebase-admin:9.2.0")

    // Module
    implementation(project(":document-db-module"))
    implementation(project(":common-messaging-module:kafka-producer"))

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")
}
tasks.test {
    useJUnitPlatform()
}

// ✅ QClass를 Intellij가 사용할 수 있도록 경로에 추가합니다
var querydslDir = "$buildDir/generated/source/kapt/main"
idea {
    module {
        val kaptMain = file(querydslDir)
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

