package com.cj.smartworker.messaging.publisher

import com.cj.smartworker.annotation.MessageAdapter
import com.cj.smartworker.kafka.model.StepCountRequest
import com.cj.smartworker.business.day_report.port.out.StepCountMessagePort
import com.cj.smartworker.domain.util.logger
import com.cj.smartworker.kafka.producer.service.KafkaProducer
import com.cj.smartworker.messaging.configuration.KafkaTopicConfigData
import org.springframework.kafka.support.SendResult
import java.util.UUID
import java.util.function.BiConsumer

@MessageAdapter
internal class StepCountMassagePublisher(
    private val kafkaProducer: KafkaProducer<String, StepCountRequest>,
    private val kafkaTopicConfigData: KafkaTopicConfigData,
): StepCountMessagePort {
    private val logger = logger()
    override fun sendStepPublish(stepCount: StepCountRequest) {
        try {
            kafkaProducer.send(
                topicName = kafkaTopicConfigData.stepCount,
                key = UUID.randomUUID().toString(),
                message = stepCount,
                callback = getCallback(
                    topicName = kafkaTopicConfigData.stepCount,
                    message = stepCount,
                )
            )
        } catch (e: Exception) {
            logger.error("Error while sending HeartRate to kafka for step count: " +
                    "${stepCount.step} error: ${e.message}")
            throw RuntimeException("걸음 수 데이터 전송에 실패했습니다.")
        }
    }

    private fun getCallback(
        topicName: String,
        message: StepCountRequest
    ): BiConsumer<SendResult<String, StepCountRequest>, Throwable?> {
        return BiConsumer{ result, ex ->
            ex?.run {
                logger.error("Kafka Massage Send Error $message to topic $topicName")
            } ?: run {
                val metadata = result.recordMetadata
                logger.info(
                    "Received new metadata Topic: ${metadata.topic()}; Partition: ${metadata.partition()}; " +
                            "Offset: ${metadata.offset()}; Timestamp: ${metadata.timestamp()}; at time: ${System.nanoTime()}"
                )
            }
        }
    }
}
