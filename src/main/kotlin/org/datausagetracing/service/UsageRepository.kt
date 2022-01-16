package org.datausagetracing.service

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import java.util.*

interface UsageRepository : ReactiveCrudRepository<Usage, UUID>