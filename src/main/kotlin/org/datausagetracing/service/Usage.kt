package org.datausagetracing.service

import org.springframework.data.annotation.Id
import java.util.*

data class Usage(
    @Id
    var id: UUID,
    var name: String
)