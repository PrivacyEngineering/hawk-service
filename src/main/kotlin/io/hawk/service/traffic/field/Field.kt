package io.hawk.service.traffic.field

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.hawk.service.traffic.mapping.MappingField
import org.hibernate.annotations.Type
import jakarta.persistence.*

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

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var legalBases: MutableList<LegalBase> = mutableListOf()

    @Column(nullable = false)
    var legalRequirement: Boolean? = false

    @Column(nullable = false)
    var contractualRegulation: Boolean? = false

    @Column(nullable = false)
    var obligationToProvide: Boolean? = false

    var consequences: String? = ""

    @JsonIgnore
    @OneToMany(mappedBy = "field", cascade = [CascadeType.REMOVE])
    var mappingFields: MutableList<MappingField> = mutableListOf()
}