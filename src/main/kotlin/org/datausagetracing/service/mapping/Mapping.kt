package org.datausagetracing.service.mapping

import org.datausagetracing.service.usage.Usage
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

    @ManyToMany
    @JoinColumn(name = "endpointId")
    var usages: MutableList<Usage> = mutableListOf()
}