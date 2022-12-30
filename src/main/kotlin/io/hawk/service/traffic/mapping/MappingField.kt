package io.hawk.service.traffic.mapping

import com.fasterxml.jackson.annotation.JsonIgnore
import io.hawk.service.traffic.field.Field
import jakarta.persistence.*

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