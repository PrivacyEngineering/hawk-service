package org.datausagetracing.service.mapping

import org.datausagetracing.service.field.Field
import org.datausagetracing.service.usage.UsageField
import org.hibernate.annotations.JoinColumnOrFormula
import org.hibernate.annotations.JoinColumnsOrFormulas
import org.hibernate.annotations.JoinFormula
import javax.persistence.*

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
    @JoinColumn(name = "endpoint_id", referencedColumnName = "endpointId")
    lateinit var mapping: Mapping

    @ManyToMany
    @JoinColumnsOrFormulas(
        JoinColumnOrFormula(formula = JoinFormula("(SELECT u.endpoint_id FROM usage u WHERE u.id = usage_id)", referencedColumnName = "endpointId")),
        JoinColumnOrFormula(column = JoinColumn(name = "phase", referencedColumnName = "phase")),
        JoinColumnOrFormula(column = JoinColumn(name = "namespace", referencedColumnName = "namespace")),
        JoinColumnOrFormula(column = JoinColumn(name = "format", referencedColumnName = "format")),
        JoinColumnOrFormula(column = JoinColumn(name = "path", referencedColumnName = "path"))
    )
    lateinit var usageFields: List<UsageField>
}