package com.cj.smartworker.messaging.consumer

import com.cj.smartworker.annotation.MessageAdapter
import com.cj.smartworker.business.step.SaveStepCountService
import com.cj.smartworker.kafka.consumer.KafkaConsumer
import com.cj.smartworker.kafka.model.StepCountRequest
import com.cj.smartworker.util.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

@MessageAdapter
internal class StepCountConsumer(
    private val saveStepCountService: SaveStepCountService,
): KafkaConsumer<StepCountRequest> {
    private val logger = logger()

    @KafkaListener(
        id = "\${kafka-consumer-config.step-count-group-id}",
        topics = ["\${heart-rate.step-count}"],
    )
    override fun receive(
        @Payload messages: List<StepCountRequest>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>,
    ) {
        logger.info("Received ${messages.size} heart rate messages with keys: $keys, partitions: $partitions and offsets: $offsets")
        saveStepCountService.saveAll(messages)
    }
}
