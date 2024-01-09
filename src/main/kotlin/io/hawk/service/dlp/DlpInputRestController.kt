package io.hawk.service.dlp

import io.hawk.dlp.common.InspectResult
import io.hawk.dlp.common.Job
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dlp")
class DlpInputRestController(
    private val dlpJobRepository: DlpJobRepository
) {
    @PostMapping
    fun input(job: Job) {
        val dlpJob = DlpJob().apply dlp@{
            id = job.id
            created = job.created
            status = job.status
            error = job.error?.replace("\u0000", "")
            results = job.results?.values?.mapNotNull {
                if(it is InspectResult) {
                    InspectDlpResult().apply {
                        id = it.id
                        this.job = this@dlp
                        timestamp = it.timestamp
                        additional = it.additional
                        findings = it.findings.map {
                            DlpFinding().apply {
                                infoType = it.infoType
                                likelihood = it.likelihood
                                occurrences = it.occurrences
                                additional = it.additional
                            }
                        }
                    }
                } else null
            } ?: emptyList()
        }
        dlpJobRepository.save(dlpJob)
    }
}