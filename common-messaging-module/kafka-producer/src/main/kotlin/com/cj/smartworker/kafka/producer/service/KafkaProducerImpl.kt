package com.cj.smartworker.kafka.producer.service

import jakarta.annotation.PreDestroy
import org.apache.kafka.common.KafkaException
import org.springframework.kafka.core.KafkaProducerException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

@Component
class KafkaProducerImpl<K : Serializable, V : Serializable>(
    private val kafkaTemplate: KafkaTemplate<K, V>,
) : KafkaProducer<K, V> {

    @PreDestroy
    fun close() {
        kafkaTemplate.destroy()
    }
    override fun send(
        topicName: String,
        key: K,
        message: V,
        callback: BiConsumer<SendResult<K, V>, Throwable?>,
    ) {
        try {
            val kafkaResultFuture: CompletableFuture<SendResult<K, V>> = kafkaTemplate.send(topicName, key, message)
            kafkaResultFuture.whenComplete(callback)
        } catch (e: KafkaException) {
            throw RuntimeException("Error on kafka producer with key: $key message: $message")
        }
    }
}
