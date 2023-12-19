package io.hawk.service.dlp

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*

@Entity
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
open class DlpResult {
    @Id
    open lateinit var id: UUID

    @Column(nullable = false)
    open lateinit var timestamp: LocalDateTime

    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb")
    open var additional: Map<String, Any?>? = null
}