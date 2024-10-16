package com.cj.smartworker.messaging.publisher

import com.cj.smartworker.annotation.MessageAdapter
import com.cj.smartworker.business.heart_rate.port.out.SendHeartRateMessagePort
import com.cj.smartworker.domain.heart_rate.entity.HeartRate
import com.cj.smartworker.domain.util.logger
import com.cj.smartworker.kafka.model.HeartRateDto
import com.cj.smartworker.kafka.producer.service.KafkaProducer
import com.cj.smartworker.messaging.configuration.KafkaTopicConfigData
import com.cj.smartworker.messaging.mapper.HeartRateMessageMapper
import org.springframework.kafka.support.SendResult
import java.util.function.BiConsumer


@MessageAdapter
internal class HeartRateMassagePublisher(
    private val kafkaProducer: KafkaProducer<String, HeartRateDto>,
    private val kafkaTopicConfigData: KafkaTopicConfigData,
    private val heartRateMessageMapper: HeartRateMessageMapper,
): SendHeartRateMessagePort {

    private val logger = logger()
    override fun publish(heartRate: HeartRate) {
        val heartRateDto = heartRateMessageMapper.toDto(heartRate)

        try {
            kafkaProducer.send(
                topicName = kafkaTopicConfigData.topicName,
                key = heartRate.heartRateId.toString(),
                message = heartRateDto,
                callback = getCallback(
                    topicName = kafkaTopicConfigData.topicName,
                    message = heartRateDto,
                )
            )
        } catch (e: Exception) {
            logger.error("Error while sending HeartRate to kafka for heartRate id: " +
                        "${heartRate.heartRateId} error: ${e.message}")
            throw RuntimeException("심박수 데이터 전송에 실패했습니다.")
        }
    }

    private fun getCallback(
        topicName: String,
        message: HeartRateDto,
    ): BiConsumer<SendResult<String, HeartRateDto>, Throwable?>{
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
