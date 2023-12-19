package io.hawk.service.dlp

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import io.hawk.dlp.common.InfoType
import io.hawk.dlp.common.Occurrence
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Type
import java.util.UUID

@Entity
class DlpFinding {
    @Id
    var id: UUID = UUID.randomUUID()

    @Enumerated
    @Column(nullable = false)
    lateinit var infoType: InfoType

    var likelihood: Double? = null

    @Column(nullable = false)
    @Type(JsonBinaryType::class)
    lateinit var occurrence: Occurrence

    @Type(JsonBinaryType::class)
    var additional: Map<String, Any?>? = null

    @ManyToOne
    @JoinColumn
    lateinit var result: InspectDlpResult
}