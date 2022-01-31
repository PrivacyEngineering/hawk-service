package org.datausagetracing.service.field

import com.fasterxml.jackson.annotation.JsonIgnore
import org.datausagetracing.service.mapping.MappingField
import javax.persistence.*

@Entity
class Field {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(length = 60, unique = true)
    lateinit var name: String

    var description: String? = null

    @Column(nullable = false)
    var personalData: Boolean = false

    @Column(nullable = false)
    var specialCategoryPersonalData: Boolean = false

    @JsonIgnore
    @OneToMany(mappedBy = "field", cascade = [CascadeType.REMOVE])
    var mappingFields: MutableList<MappingField> = mutableListOf()
}