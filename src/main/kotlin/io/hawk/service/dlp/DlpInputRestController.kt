package io.hawk.service.dlp

import io.hawk.dlp.common.InspectResult
import io.hawk.dlp.common.Job
import io.hawk.dlp.common.Result
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/dlp")
class DlpInputRestController(
    private val dlpJobRepository: DlpJobRepository,
    private val dlpResultRepository: DlpResultRepository
) {
    @PostMapping
    fun input(job: Job) {
        val dlpJob = DlpJob().apply dlp@{
            id = job.id
            created = job.created
            status = job.status
            error = job.error?.replace("\u0000", "")
        }
        dlpJobRepository.save(dlpJob)
    }

    @PostMapping("/{jobId}/result")
    fun result(@PathVariable jobId: UUID, result: Result) {
        val dlpJob = dlpJobRepository.findById(jobId).orElseThrow { error("Job does not exist") }
        if(result is InspectResult) {
            val dlpResult = InspectDlpResult().apply {
                id = result.id
                job = dlpJob
                timestamp = result.timestamp
                additional = result.additional
                findings = result.findings.map {
                    DlpFinding().apply {
                        infoType = it.infoType
                        likelihood = it.likelihood
                        occurrences = it.occurrences
                        additional = it.additional
                    }
                }
            }
            dlpResultRepository.save(dlpResult)
        }
    }
}