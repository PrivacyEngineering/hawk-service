package io.hawk.service.traffic.usage

import java.util.*
import jakarta.persistence.*

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
    @JoinColumn(name = "usage_id")
    lateinit var usage: Usage
}