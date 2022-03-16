package org.datausagetracing.service.mapping

import com.fasterxml.jackson.annotation.JsonIgnore
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
    @JsonIgnore
    @JoinColumn(name = "endpoint_id", referencedColumnName = "endpointId")
    lateinit var mapping: Mapping
}