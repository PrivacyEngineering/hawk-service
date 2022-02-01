package org.datausagetracing.service.mapping

import org.datausagetracing.service.usage.Usage
import javax.persistence.*

@Entity
class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(nullable = false, unique = true)
    lateinit var endpointId: String

    @OneToMany(mappedBy = "mapping", cascade = [CascadeType.REMOVE])
    var fields: MutableList<MappingField> = mutableListOf()

    @OneToMany
    @JoinColumn(
        name = "endpointId",
        referencedColumnName = "endpointId",
        insertable = false,
        updatable = false
    )
    var usages: MutableList<Usage> = mutableListOf()
}