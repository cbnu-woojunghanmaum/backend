package com.cj.smartworker.kafka.producer

import com.cj.smartworker.kafka.data.KafkaConfigData
import com.cj.smartworker.kafka.data.KafkaProducerConfigData
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import java.io.Serializable

@Configuration
class KafkaProducerConfig <K: Serializable, V: Serializable>(
    private val kafkaConfigData: KafkaConfigData,
    private val kafkaProducerConfigData: KafkaProducerConfigData,
) {
    @Bean
    fun producerConfig(): MutableMap<String, Any> {

        return mutableMapOf(
            BOOTSTRAP_SERVERS_CONFIG to kafkaConfigData.bootstrapServers,
            kafkaConfigData.schemaRegistryUrlKey to kafkaConfigData.schemaRegistryUrl,
            KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
            BATCH_SIZE_CONFIG to kafkaProducerConfigData.batchSize * kafkaProducerConfigData.batchSizeBoostFactor,
            LINGER_MS_CONFIG to kafkaProducerConfigData.lingerMs,
            COMPRESSION_TYPE_CONFIG to kafkaProducerConfigData.compressionType,
            ACKS_CONFIG to kafkaProducerConfigData.acks,
            REQUEST_TIMEOUT_MS_CONFIG to kafkaProducerConfigData.requestTimeoutMs,
            RETRIES_CONFIG to kafkaProducerConfigData.retryCount,
        )
    }

    @Bean
    fun producerFactory(): ProducerFactory<K, V> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<K, V> = KafkaTemplate(producerFactory())
}
