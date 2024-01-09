package io.hawk.service.dlp

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.hawk.dlp.common.InfoType
import io.hawk.dlp.common.Occurrence
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.util.*

@Entity
class DlpFinding {
    @Id
    var id: UUID = UUID.randomUUID()

    @Column(nullable = false)
    lateinit var infoType: InfoType

    var likelihood: Double? = null

    @Type(JsonBinaryType::class)
    @Column(nullable = false, columnDefinition = "jsonb")
    lateinit var occurrences: List<Occurrence>

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    var additional: Map<String, Any?>? = null

    @ManyToOne
    @JoinColumn
    @JsonIdentityReference(alwaysAsId=true)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property="id")
    lateinit var result: InspectDlpResult

}