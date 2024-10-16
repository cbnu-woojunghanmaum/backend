package com.cj.smartworker.benchmark

import com.cj.smartworker.mongo.heart_rate.entity.HeartRateDocument
import com.cj.smartworker.mongo.heart_rate.repository.HeartRateMongoRepository
import com.cj.smartworker.mongo.heart_rate.value.HeartRateDataId
import com.cj.smartworker.testbase.IntegrationTestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlinx.coroutines.runBlocking
class SaveHeartRateBenchmark @Autowired constructor(
    private val heartRateMongoRepository: HeartRateMongoRepository,
): IntegrationTestBase() {

    /**
     * 10,000개의 데이터를 저장하는데 걸리는 시간을 측정합니다.
     * 1000개 씩 10번 저장합니다.
     */
    @Test
    fun `벤치마크 10,000개의 데이터를 1000개 씩 저장하는 성능 측정`() {
        val iterations = 5 // 반복 횟수
        val warmupIterations = 2 // 워밍업 반복 횟수

        val heartRateData = generateTestData(10000)

        // 워밍업 실행
        repeat(warmupIterations) {
            runBenchmark(heartRateData, 1_000)
        }

        // 실제 벤치마크 실행
        val results = mutableListOf<Long>()
        repeat(iterations) {
            val duration = runBenchmark(heartRateData, 1_000)
            results.add(duration)
            println("Iteration ${it + 1}: ${duration / 1_000_000.0} ms")
        }

        // 결과 분석
        val average = results.average()
        val min = results.minOrNull()
        val max = results.maxOrNull()

        println("평균 실행 시간: ${average / 1_000_000.0} ms")
        println("최소 실행 시간: ${min?.div(1_000_000.0)} ms")
        println("최대 실행 시간: ${max?.div(1_000_000.0)} ms")
    }

    /**
     * 10,000개의 데이터를 저장하는데 걸리는 시간을 측정합니다.
     * 500개 씩 20번 저장합니다.
     */
    @Test
    fun `벤치마크 10,000개의 데이터를 500개 씩 저장하는 성능 측정`() {
        val iterations = 5 // 반복 횟수
        val warmupIterations = 2 // 워밍업 반복 횟수

        val heartRateData = generateTestData(10000)

        // 워밍업 실행
        repeat(warmupIterations) {
            runBenchmark(heartRateData, 500)
        }

        // 실제 벤치마크 실행
        val results = mutableListOf<Long>()
        repeat(iterations) {
            val duration = runBenchmark(heartRateData, 500)
            results.add(duration)
            println("Iteration ${it + 1}: ${duration / 1_000_000.0} ms")
        }

        // 결과 분석
        val average = results.average()
        val min = results.minOrNull()
        val max = results.maxOrNull()

        println("평균 실행 시간: ${average / 1_000_000.0} ms")
        println("최소 실행 시간: ${min?.div(1_000_000.0)} ms")
        println("최대 실행 시간: ${max?.div(1_000_000.0)} ms")
    }

    /**
     * 10,000개의 데이터를 저장하는데 걸리는 시간을 측정합니다.
     * 1개 씩 10,000번 저장합니다.
     */
    @Test
    fun `벤치마크 10,000개의 데이터를 한 개씩 저장하는 성능 측정`() {
        val iterations = 5 // 반복 횟수
        val warmupIterations = 2 // 워밍업 반복 횟수
        val dataCount = 10000 // 저장할 데이터 수

        // 워밍업 실행
        repeat(warmupIterations) {
            runBenchmark(dataCount)
        }

        // 실제 벤치마크 실행
        val results = mutableListOf<Long>()
        repeat(iterations) {
            val duration = runBenchmark(dataCount)
            results.add(duration)
            println("Iteration ${it + 1}: ${duration / 1_000_000.0} ms")
        }

        // 결과 분석
        val average = results.average()
        val min = results.minOrNull()
        val max = results.maxOrNull()

        println("평균 실행 시간: ${average / 1_000_000.0} ms")
        println("최소 실행 시간: ${min?.div(1_000_000.0)} ms")
        println("최대 실행 시간: ${max?.div(1_000_000.0)} ms")
    }


    private fun runBenchmark(data: List<HeartRateDocument>, chunkSize: Int): Long {
        // GC 실행을 유도하여 GC의 영향을 최소화
        System.gc()

        return measureNanoTime {
            runBlocking {
                data.chunked(chunkSize).forEach { chunk ->
                    heartRateMongoRepository.saveAll(chunk)
                }
            }
        }
    }

    private fun generateTestData(count: Int): List<HeartRateDocument> {
        return (1..count).map {
            HeartRateDocument(
                id = HeartRateDataId(
                    memberId = 1,
                    timestamp = LocalDateTime.now().plusSeconds(it.toLong()),
                ),
                heartRate = Random.nextDouble(60.0, 100.0),
            )
        }
    }

    private fun runBenchmark(count: Int): Long {
        // GC 실행을 유도하여 GC의 영향을 최소화
        System.gc()

        return measureNanoTime {
            runBlocking {
                repeat(count) { i ->
                    val heartRateData = generateSingleTestData(i)
                    heartRateMongoRepository.save(heartRateData)
                }
            }
        }
    }

    private fun generateSingleTestData(index: Int): HeartRateDocument {
        return HeartRateDocument(
            id = HeartRateDataId(
                memberId = 1,
                timestamp = LocalDateTime.now().plusSeconds(index.toLong()),
            ),
            heartRate = Random.nextDouble(60.0, 100.0),
        )
    }
}
