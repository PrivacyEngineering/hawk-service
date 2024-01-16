package io.hawk.service.dlp

import io.hawk.dlp.common.InspectResult
import io.hawk.dlp.common.Job
import io.hawk.dlp.common.Result
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
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

    @PostMapping("/{jobId}/result/inspect")
    fun result(@PathVariable jobId: UUID, result: InspectResult) {
        val dlpJob = dlpJobRepository.findById(jobId).orElseThrow { error("Job does not exist") }
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
