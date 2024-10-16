package com.cj.smartworker.business.step

import com.cj.smartworker.kafka.model.StepCountRequest
import com.cj.smartworker.mongo.step.entity.StepCountDocument
import com.cj.smartworker.mongo.step.repository.StepCountDocumentRepository
import com.cj.smartworker.util.toKstLocalDateTime
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class SaveStepCountService(
    private val stepMongoRepository: StepCountDocumentRepository,
) {
    fun saveAll(stepCountRequestList: List<StepCountRequest>) {
        val map = stepCountRequestList.map { stepCountRequest ->
            StepCountDocument(
                memberId = stepCountRequest.memberId,
                stepCount = stepCountRequest.step,
                timestamp = Instant.ofEpochSecond(stepCountRequest.timestamp).toKstLocalDateTime(),
            )
        }
        stepMongoRepository.saveAll(map)
    }
}
