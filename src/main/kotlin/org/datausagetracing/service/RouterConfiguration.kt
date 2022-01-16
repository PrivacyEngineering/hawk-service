package org.datausagetracing.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import java.util.*

@Configuration
class RouterConfiguration {
    @Bean
    fun usageRouter(usageHandler: UsageHandler) = router {
        "/api/usage".nest {
            GET("/{id}")
                .and { it.pathVariable("id").toUuidOrNull() != null }
                .invoke(usageHandler::findUsage)
        }
    }
}

fun String.toUuidOrNull(): UUID? = try {
    UUID.fromString(this)
} catch (throwable: Throwable) {
    null
}