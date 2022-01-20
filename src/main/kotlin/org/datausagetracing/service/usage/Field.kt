package org.datausagetracing.service.usage

import java.util.*
import javax.persistence.*

@Entity
class Field {
    @Id
    lateinit var id: UUID

    lateinit var namespace: String

    lateinit var format: String

    lateinit var path: String

    var count: Int = 0

    @ManyToOne
    @JoinColumn(name = "usageId")
    lateinit var usage: Usage
}