package org.datausagetracing.service

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor


@OpenAPIDefinition(
    info = Info(
        title = "Hawk-Service",
        version = "1.1.0"
    )
)
@EnableAsync
@EnableScheduling
@SpringBootApplication
class DataUsageTracingServiceApplication {
    @Bean
    fun taskExecutor() = ConcurrentTaskExecutor()
}

fun main(args: Array<String>) {
    runApplication<DataUsageTracingServiceApplication>(*args)
}
