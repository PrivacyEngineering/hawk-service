package io.hawk.service.module.release

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/release")
class ReleaseController(
    private val releaseCollector: ReleaseCollector
) {
    @GetMapping("/open")
    fun openReleases() = releaseCollector.openReleases()

    @GetMapping("/completed")
    fun completedReleases() = releaseCollector.completedReleases()
}