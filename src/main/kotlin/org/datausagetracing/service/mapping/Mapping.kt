package org.datausagetracing.service.mapping

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class Mapping {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(nullable = false, unique = true)
    lateinit var endpointId: String

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var purposes: MutableList<Purpose> = mutableListOf()

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var legitimateInterests: MutableList<String> = mutableListOf()

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var recipients: MutableList<String> = mutableListOf()

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var storage: MutableList<Storage> = mutableListOf()

    @OneToMany(mappedBy = "mapping", cascade = [CascadeType.REMOVE])
    var fields: MutableList<MappingField> = mutableListOf()
}