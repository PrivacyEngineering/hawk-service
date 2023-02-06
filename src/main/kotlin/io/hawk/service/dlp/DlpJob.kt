package io.hawk.service.dlp

import io.hawk.dlp.common.JobStatus
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.UUID

@Entity
class DlpJob {
    @Id
    lateinit var id: UUID

    var app: String? = null

    @Column(nullable = false)
    var created: LocalDateTime = LocalDateTime.now()

    @Enumerated
    @Column(nullable = false)
    lateinit var status: JobStatus

    @Column(length = 2_000)
    var error: String? = null
}