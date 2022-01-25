package org.datausagetracing.service.mapping

import javax.persistence.*

@Entity
class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(nullable = false)
    lateinit var endpointId: String

    @OneToMany(mappedBy = "mapping", cascade = [CascadeType.REMOVE])
    var fields: MutableList<MappingField> = mutableListOf()
}