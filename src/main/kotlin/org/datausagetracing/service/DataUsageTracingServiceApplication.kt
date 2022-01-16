package org.datausagetracing.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataUsageTracingServiceApplication

fun main(args: Array<String>) {
    runApplication<DataUsageTracingServiceApplication>(*args)
}
