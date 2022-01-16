package org.datausagetracing.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.json

@Service
class UsageHandler(
    val usageRepository: UsageRepository
) {
    fun findUsage(serverRequest: ServerRequest) = ok().json().body<Usage>(
        usageRepository
            .findById(serverRequest.pathVariable("id").toUuidOrNull()!!),
    )
}