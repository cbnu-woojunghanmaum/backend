package com.cj.smartworker.messaging.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "heart-rate")
data class KafkaTopicConfigData(
    val topicName: String,
    val stepCount: String,
)
