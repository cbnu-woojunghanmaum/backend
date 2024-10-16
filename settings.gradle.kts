rootProject.name = "smartworker-backend-api"

include(":api-module")
include(":save-heart-rate-module")
include(":document-db-module")
include(":common-messaging-module")
include(":common-messaging-module:kafka-config-data")
include(":common-messaging-module:kafka-consumer")
include(":common-messaging-module:kafka-model")
include(":common-messaging-module:kafka-producer")

pluginManagement {
    val kotlinVersion : String by settings
    val springBootVersion : String by settings
    val springDependencyManagementVersion : String by settings
    val jvmVersion : String by settings
    val kaptVersion: String by settings
    val pluginSpring: String by settings
    val pluginJpa: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "jvm" -> useVersion(jvmVersion)
                "kapt" -> useVersion(kaptVersion)
                "plugin.spring" -> useVersion(pluginSpring)
                "plugin.jpa" -> useVersion(pluginJpa)
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.gradle.core" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
            }
        }
    }
}
