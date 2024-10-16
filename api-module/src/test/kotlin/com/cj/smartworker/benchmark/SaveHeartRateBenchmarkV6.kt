package com.cj.smartworker.benchmark

import com.cj.smartworker.mongo.heart_rate.entity.HeartRateDocument
import com.cj.smartworker.mongo.heart_rate.repository.HeartRateMongoRepository
import com.cj.smartworker.mongo.heart_rate.value.HeartRateDataId
import com.cj.smartworker.testbase.IntegrationTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import kotlin.random.Random
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.withTimeout

class SaveHeartRateBenchmarkV6 @Autowired constructor(
    private val heartRateMongoRepository: HeartRateMongoRepository,
) : IntegrationTestBase() {

    private val concurrencyLimit = 10 // 동시에 실행될 수 있는 최대 작업 수

    @Test
    fun `비동기 벤치마크 1분동안 데이터를 한 개씩 저장하는 성능 측정`() = runBlocking {
        runAsyncBenchmark("비동기 단일 저장", 1) { index -> saveOneByOne(index) }
    }

    @Test
    fun `비동기 벤치마크 1분 동안 데이터를 500개씩 저장하는 성능 측정`() = runBlocking {
        runAsyncBenchmark("비동기 500개씩 저장", 500) { index -> saveBatch(index, 500) }
    }

    private suspend fun runAsyncBenchmark(name: String, batchSize: Int, saveFunction: suspend (Long) -> Unit) {
        val count = AtomicLong(0)
        val duration = 30_000L // 1분
        val gracePeriod = 5_000L // 5초의 유예 기간

        val elapsed = measureTimeMillis {
            try {
                withTimeout(duration + gracePeriod) {
                    coroutineScope {
                        val jobs = List(concurrencyLimit) {
                            launch {
                                val endTime = System.currentTimeMillis() + duration
                                while (System.currentTimeMillis() < endTime && isActive) {
                                    try {
                                        withTimeout(10000) { // 각 저장 작업에 3초 타임아웃 설정
                                            saveFunction(count.get())
                                            count.addAndGet(batchSize.toLong())
                                        }
                                    } catch (e: TimeoutCancellationException) {
                                        println("저장 작업 타임아웃: ${e.message}")
                                    }
                                }
                            }
                        }
//                        delay(duration)
//                        jobs.forEach { it.cancel() }
//                        jobs.forEach { it.join() }
                        jobs.joinAll()
                    }
                }
            } catch (e: TimeoutCancellationException) {
                println("전체 벤치마크 타임아웃: ${e.message}")
            }
        }

        println("$name 결과:")
        println("총 저장된 데이터 수: ${count.get()}")
        println("실제 실행 시간: ${elapsed}ms")
        println("초당 평균 저장 데이터 수: ${count.get() / (elapsed / 1000.0)}")
    }

    private suspend fun saveOneByOne(index: Long) {
        val heartRateData = generateSingleTestData(index)
//        delay(100)
        heartRateMongoRepository.save(heartRateData)
    }

    private suspend fun saveBatch(startIndex: Long, batchSize: Int) {
        val heartRateDataBatch = (startIndex until startIndex + batchSize).map { index ->
            generateSingleTestData(index)
        }
//        delay(200)
        heartRateMongoRepository.saveAll(heartRateDataBatch)
    }

    private fun generateSingleTestData(index: Long): HeartRateDocument {
        return HeartRateDocument(
            id = HeartRateDataId(
                memberId = 1,
                timestamp = LocalDateTime.now().plusSeconds(index),
            ),
            heartRate = 100.0,
        )
    }
}
