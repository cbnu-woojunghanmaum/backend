plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") apply false
}

val projectGroup: String by project
val projectVersion: String by project

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    group = projectGroup
    version = projectVersion
    repositories {
        mavenCentral()
        maven {
            url = uri("https://packages.confluent.io/maven")
        }
    }
}



subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.testcontainers:junit-jupiter")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}


kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
