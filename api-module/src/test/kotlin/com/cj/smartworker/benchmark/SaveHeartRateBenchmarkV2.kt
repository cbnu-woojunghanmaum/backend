package com.cj.smartworker.benchmark

import com.cj.smartworker.mongo.heart_rate.entity.HeartRateDocument
import com.cj.smartworker.mongo.heart_rate.repository.HeartRateMongoRepository
import com.cj.smartworker.mongo.heart_rate.value.HeartRateDataId
import com.cj.smartworker.testbase.IntegrationTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import kotlin.random.Random
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit

class SaveHeartRateBenchmarkV2 @Autowired constructor(
    private val heartRateMongoRepository: HeartRateMongoRepository,
) : IntegrationTestBase() {

    @Test
    fun `벤치마크 30초 동안 데이터를 한 개씩 저장하는 성능 측정`() {
        val duration = TimeUnit.SECONDS.toMillis(30)
        runBenchmark("단일 저장", duration, 1) { index -> saveOneByOne(index) }
    }

    @Test
    fun `벤치마크 30초 동안 데이터를 500개씩 저장하는 성능 측정`() {
        val duration = TimeUnit.SECONDS.toMillis(30)
        runBenchmark("500개씩 저장", duration, 500) { index -> saveBatch(index, 500) }
    }

    private fun runBenchmark(name: String, duration: Long, batchSize: Int, saveFunction: (Long) -> Unit) {
        val startTime = System.currentTimeMillis()
        var count = 0L

        while (System.currentTimeMillis() - startTime < duration) {
            saveFunction(count)
            count += batchSize
        }

        println("$name 결과:")
        println("총 저장된 데이터 수: $count")
        println("초당 평균 저장 데이터 수: ${count / (duration / 1000.0)}")
    }

    private fun saveOneByOne(index: Long) {
        val heartRateData = generateSingleTestData(index)
        runBlocking {
            heartRateMongoRepository.save(heartRateData)
        }
    }

    private fun saveBatch(startIndex: Long, batchSize: Int) {
        val heartRateDataBatch = (startIndex until startIndex + batchSize).map { index ->
            generateSingleTestData(index)
        }
        runBlocking {
            heartRateMongoRepository.saveAll(heartRateDataBatch)
        }
    }

    private fun generateSingleTestData(index: Long): HeartRateDocument {
        return HeartRateDocument(
            id = HeartRateDataId(
                memberId = 1,
                timestamp = LocalDateTime.now().plusSeconds(index),
            ),
            heartRate = Random.nextDouble(60.0, 100.0),
        )
    }
}
