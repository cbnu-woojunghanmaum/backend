package com.cj.smartworker.mongo.step.repository

import com.cj.smartworker.mongo.step.entity.StepCountDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StepCountDocumentRepository: MongoRepository<StepCountDocument, String> {
}
