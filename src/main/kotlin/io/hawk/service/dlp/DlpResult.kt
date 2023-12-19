package io.hawk.service.dlp

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class DlpResult {
    @Id
    open lateinit var id: UUID

    @Column(nullable = false)
    open lateinit var timestamp: LocalDateTime

    @Type(JsonBinaryType::class)
    open var additional: Map<String, Any?>? = null
}