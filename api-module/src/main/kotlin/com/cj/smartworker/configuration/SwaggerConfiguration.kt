package com.cj.smartworker.configuration

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfiguration {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("SmartWorker API")
                .description("SmartWorker API 문서")
                .version("v1"))
    }

    @Bean
    fun publicAPI(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public-apis")
            .pathsToMatch("/api/**")
            .build()
    }
}
