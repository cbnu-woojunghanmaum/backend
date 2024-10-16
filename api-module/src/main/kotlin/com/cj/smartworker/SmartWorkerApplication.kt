package com.cj.smartworker

import com.cj.smartworker.messaging.configuration.KafkaTopicConfigData
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableConfigurationProperties(value = [KafkaTopicConfigData::class])
@EnableJpaRepositories(basePackages = ["com.cj.smartworker.dataaccess"])
@SpringBootApplication
class SmartWorkerApplication

fun main(args: Array<String>) {
    runApplication<SmartWorkerApplication>(*args)
}
