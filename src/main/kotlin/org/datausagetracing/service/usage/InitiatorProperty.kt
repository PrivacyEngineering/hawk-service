package org.datausagetracing.service.usage

import java.util.*
import javax.persistence.*

@Entity
class InitiatorProperty {
    @Id
    lateinit var id: UUID

    @Column(nullable = false)
    lateinit var side: String

    @Column(nullable = false)
    lateinit var phase: String

    @Column(nullable = false)
    lateinit var key: String

    @Column(nullable = false)
    lateinit var value: String

    @ManyToOne
    @JoinColumn(name = "usageId")
    lateinit var usage: Usage
}