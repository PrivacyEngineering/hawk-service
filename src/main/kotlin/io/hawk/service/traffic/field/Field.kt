package io.hawk.service.traffic.field

import com.fasterxml.jackson.annotation.JsonIgnore
import com.vladmihalcea.hibernate.type.array.EnumArrayType
import com.vladmihalcea.hibernate.type.array.internal.AbstractArrayType
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.hawk.dlp.common.InfoType
import io.hawk.service.dlp.DlpFinding
import io.hawk.service.traffic.mapping.MappingField
import jakarta.persistence.*
import org.hibernate.annotations.JoinFormula
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.Type

@Entity
class Field {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Int? = null

    @Column(length = 60, unique = true)
    lateinit var name: String

    var description: String? = null

    @Type(value = EnumArrayType::class,
        parameters = [
            Parameter(
                name = AbstractArrayType.SQL_ARRAY_TYPE,
                value = "text"
            )
        ]
    )
    @Column(columnDefinition = "text[]")
    lateinit var infoTypes: Array<InfoType>

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

    @JsonIgnore
    @ManyToMany
    @JoinFormula(value = """
    (SELECT 1 FROM field 
     WHERE field.id = field_id 
     AND dlp_finding.info_type = ANY(field.info_types))
    """, referencedColumnName = "id")
    var findings: MutableList<DlpFinding> = mutableListOf()
}