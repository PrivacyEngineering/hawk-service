package io.hawk.service.traffic.usage

import io.hawk.service.traffic.mapping.MappingField
import org.hibernate.annotations.JoinColumnOrFormula
import org.hibernate.annotations.JoinColumnsOrFormulas
import org.hibernate.annotations.JoinFormula
import java.util.*
import jakarta.persistence.*

@Entity
class UsageField {
    @Id
    lateinit var id: UUID

    @Column(nullable = false)
    lateinit var side: String

    @Column(nullable = false)
    lateinit var phase: String

    @Column(nullable = false)
    lateinit var namespace: String

    @Column(nullable = false)
    lateinit var format: String

    @Column(nullable = false)
    lateinit var path: String

    @Column(nullable = false)
    var count: Int = 0

    @ManyToOne
    @JoinColumn(name = "usage_id")
    lateinit var usage: Usage
}