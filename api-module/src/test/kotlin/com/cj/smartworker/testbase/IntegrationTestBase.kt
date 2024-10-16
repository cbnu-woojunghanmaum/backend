package com.cj.smartworker.testbase

import com.cj.smartworker.testbase.cleaner.MongoCleaner
import com.cj.smartworker.testbase.cleaner.RDBCleaner
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MongoDBContainer
import java.time.Duration

@SpringBootTest
@ContextConfiguration(initializers = [IntegrationTestBase.IntegrationTestInitializer::class])
abstract class IntegrationTestBase {

    @Autowired
    private lateinit var rdbCleaner: RDBCleaner

    @Autowired
    private lateinit var mongoCleaner: MongoCleaner

    @BeforeEach
    fun beforeEach() {
        rdbCleaner.execute()
        mongoCleaner.cleanAll()
    }

    companion object {
        private val MONGO_CONTAINER = MongoDBContainer("mongo:latest")
            .apply { withStartupTimeout(Duration.ofSeconds(300)) }
            .apply { withReuse(true) }
    }

    class IntegrationTestInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            MONGO_CONTAINER.start()

            val properties: Map<String, String> = hashMapOf(
                "spring.datasource.driver-class-name" to "org.testcontainers.jdbc.ContainerDatabaseDriver",
                "spring.datasource.url" to "jdbc:tc:mysql:8.0:///test?TC_REUSABLE=true",
//                "spring.data.mongodb.uri" to MONGO_CONTAINER.replicaSetUrl,
                "spring.data.mongodb.uri" to "mongodb://ec2-43-203-200-1.ap-northeast-2.compute.amazonaws.com:27018",
                "spring.data.mongodb.database" to "benchmark",
//                "spring.data.mongodb.database" to "smart_worker",
                "spring.data.mongodb.auto-index-creation" to true.toString(),
                "sms.from" to "01012341234",
                "sms.apiKey" to "test",
                "sms.apiSecret" to "test",
                "sms.serviceDomain" to "https://test.com",
            )

            TestPropertyValues.of(properties).applyTo(applicationContext)
        }

    }
}
