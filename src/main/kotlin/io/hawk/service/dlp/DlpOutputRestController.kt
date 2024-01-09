package io.hawk.service.dlp

import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/api/dlp")
class DlpOutputRestController(
    private val dlpJobRepository: DlpJobRepository,
    private val dlpResultRepository: DlpResultRepository
) {
    @GetMapping
    fun list(): List<DlpJob> = dlpJobRepository.findAll(Sort.by(Sort.Direction.DESC, "created"))

    @GetMapping("/results/{id}")
    fun show(@PathVariable id: UUID): DlpResult =
        dlpResultRepository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }
}