package com.cj.smartworker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = ["com.cj.smartworker.mongo"])
class HeartRateApplication

fun main(args: Array<String>) {
    runApplication<HeartRateApplication>(*args)
}
