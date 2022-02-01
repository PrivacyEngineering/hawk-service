package org.datausagetracing.service.mapping

import org.datausagetracing.service.field.Field
import javax.persistence.*

@Entity
class MappingField {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @ManyToOne
    @JoinColumn(name = "field_id")
    lateinit var field: Field

    @Column(nullable = false)
    lateinit var phase: String

    @Column(nullable = false)
    lateinit var namespace: String

    @Column(nullable = false)
    lateinit var format: String

    @Column(nullable = false)
    lateinit var path: String

    @ManyToOne
    @JoinColumn(name = "mapping_id")
    lateinit var mapping: Mapping
}