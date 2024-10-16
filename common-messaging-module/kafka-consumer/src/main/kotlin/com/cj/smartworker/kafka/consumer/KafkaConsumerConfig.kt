package com.cj.smartworker.kafka.consumer

import com.cj.smartworker.kafka.data.KafkaConfigData
import com.cj.smartworker.kafka.data.KafkaConsumerConfigData
import com.cj.smartworker.kafka.model.HeartRateDto
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import java.io.Serializable

@Configuration
class KafkaConsumerConfig<K : Serializable, V : Serializable>(
    private val kafkaConfigData: KafkaConfigData,
    private val kafkaConsumerConfigData: KafkaConsumerConfigData,
) {
    @Bean
    fun consumerConfigs(): MutableMap<String, Any> {
        return mutableMapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaConfigData.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfigData.keyDeserializer,
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to kafkaConsumerConfigData.valueDeserializer,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to kafkaConsumerConfigData.autoOffsetReset,
            kafkaConfigData.schemaRegistryUrlKey to kafkaConfigData.schemaRegistryUrl,
            ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to kafkaConsumerConfigData.sessionTimeoutMs,
            ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG to kafkaConsumerConfigData.heartbeatIntervalMs,
            ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to kafkaConsumerConfigData.maxPollIntervalMs,
            ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG to kafkaConsumerConfigData.maxPartitionFetchBytesDefault *
                    kafkaConsumerConfigData.maxPartitionFetchBytesBoostFactor,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to kafkaConsumerConfigData.maxPollRecords,
            "spring.kafka.consumer.properties.spring.json.trusted.packages" to "*",
        )
    }
    @Bean
    fun consumerFactory(): ConsumerFactory<K, V> {
        val jsonDeserializer = JsonDeserializer<V>()
        jsonDeserializer.addTrustedPackages("*")
        return DefaultKafkaConsumerFactory(
            consumerConfigs(),
            null,
            jsonDeserializer
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> {
        val factory = ConcurrentKafkaListenerContainerFactory<K, V>()
        factory.consumerFactory = consumerFactory()
        factory.isBatchListener = kafkaConsumerConfigData.batchListener
        factory.setConcurrency(kafkaConsumerConfigData.concurrencyLevel)
        factory.setAutoStartup(kafkaConsumerConfigData.autoStartup)
        factory.containerProperties.pollTimeout = kafkaConsumerConfigData.pollTimeoutMs
        return factory
    }
}
