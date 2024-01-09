package io.hawk.service.dlp

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.hawk.dlp.common.JobStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class DlpJob {
    @Id
    lateinit var id: UUID

    var app: String? = null

    @Column(nullable = false)
    var created: LocalDateTime = LocalDateTime.now()

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    lateinit var status: JobStatus

    @Column(columnDefinition = "TEXT")
    var error: String? = null

    @JvmSuppressWildcards
    @OneToMany(mappedBy = "job", cascade = [CascadeType.ALL])
    @JsonSerialize(using = DlpResultOverviewSerializer::class)
    var results: MutableList<DlpResult> = mutableListOf()

    class DlpResultOverviewSerializer : JsonSerializer<List<DlpResult>>() {
        override fun serialize(result: List<DlpResult>, generator: JsonGenerator, provider: SerializerProvider) {
            generator.writeArray(result.map { it.id.toString() }.toTypedArray(), 0, result.size)
        }

    }
}