package io.hawk.service.dlp

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

    @OneToMany(mappedBy = "job")
    lateinit var results: List<out DlpResult>
}