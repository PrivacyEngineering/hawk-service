package io.hawk.service.traffic.mapping

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import jakarta.persistence.*

@Entity
class Mapping : java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @NaturalId
    @Column(nullable = false, unique = true)
    lateinit var endpointId: String

    @JsonIgnore
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var purposes: MutableList<Purpose> = mutableListOf()

    @JsonIgnore
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var legitimateInterests: MutableList<String> = mutableListOf()

    @JsonIgnore
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var recipients: MutableList<String> = mutableListOf()

    @JsonIgnore
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var storage: MutableList<Storage> = mutableListOf()

    @OneToMany(mappedBy = "mapping", fetch = FetchType.EAGER)
    var fields: MutableList<MappingField> = mutableListOf()
}