import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api(project(":common-messaging-module:kafka-config-data"))
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}
