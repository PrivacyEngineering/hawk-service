package org.datausagetracing.service

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
    info = Info(
        title = "Hawk-Service",
        version = "1.1.0"
    )
)
@SpringBootApplication
class DataUsageTracingServiceApplication

fun main(args: Array<String>) {
    runApplication<DataUsageTracingServiceApplication>(*args)
}
