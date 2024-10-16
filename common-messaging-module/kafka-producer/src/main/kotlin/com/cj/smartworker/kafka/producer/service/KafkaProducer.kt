package com.cj.smartworker.kafka.producer.service

import org.springframework.kafka.support.SendResult
import java.io.Serializable
import java.util.function.BiConsumer

interface KafkaProducer<K: Serializable, V: Serializable> {
    fun send(
        topicName: String,
        key: K,
        message: V,
        callback: BiConsumer<SendResult<K, V>, Throwable?>,
    )
}
