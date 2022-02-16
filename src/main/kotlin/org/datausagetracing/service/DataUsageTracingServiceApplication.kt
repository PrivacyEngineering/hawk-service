package org.datausagetracing.service

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@OpenAPIDefinition(
    info = Info(
        title = "Hawk-Service",
        version = "1.1.0"
    )
)
@EnableScheduling
@SpringBootApplication
class DataUsageTracingServiceApplication

fun main(args: Array<String>) {
    runApplication<DataUsageTracingServiceApplication>(*args)
}
