package com.cj.smartworker.messaging.consumer

import com.cj.smartworker.annotation.MessageAdapter
import com.cj.smartworker.business.heart_rate.SaveHeartRateService
import com.cj.smartworker.kafka.consumer.KafkaConsumer
import com.cj.smartworker.kafka.model.HeartRateDto
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import com.cj.smartworker.util.logger
import org.springframework.kafka.annotation.KafkaListener

@MessageAdapter
internal class HeartRateConsumer(
    private val saveHeartRateService: SaveHeartRateService,
): KafkaConsumer<HeartRateDto> {
    private val logger = logger()


    @KafkaListener(
        id = "\${kafka-consumer-config.heart-rate-group-id}",
        topics = ["\${heart-rate.topic-name}"],
    )
    override fun receive(
        @Payload messages: List<HeartRateDto>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
    ) {
        logger.info("Received ${messages.size} heart rate messages with keys: $keys, partitions: $partitions and offsets: $offsets")
        saveHeartRateService.saveAll(messages)
    }
}
