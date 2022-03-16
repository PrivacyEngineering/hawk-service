package org.datausagetracing.service.mapping

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import javax.persistence.*

@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class Mapping : java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @NaturalId
    @Column(nullable = false, unique = true)
    lateinit var endpointId: String

    @JsonIgnore
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var purposes: MutableList<Purpose> = mutableListOf()

    @JsonIgnore
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var legitimateInterests: MutableList<String> = mutableListOf()

    @JsonIgnore
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var recipients: MutableList<String> = mutableListOf()

    @JsonIgnore
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    var storage: MutableList<Storage> = mutableListOf()

    @OneToMany(mappedBy = "mapping", fetch = FetchType.EAGER)
    var fields: MutableList<MappingField> = mutableListOf()
}