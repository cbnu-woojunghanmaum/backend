package com.cj.smartworker.kafka.consumer

import java.io.Serializable

interface KafkaConsumer <T: Serializable> {
    fun receive(
        messages: List<T>,
        keys: List<String>,
        partitions: List<Int>,
        offsets: List<Long>,
    )
}
