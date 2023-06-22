package org.datausagetracing.service.release

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.CrossOrigin

@RestController
@RequestMapping("/api/release")
@CrossOrigin(origins = arrayOf("*"))
class ReleaseController(
    private val releaseCollector: ReleaseCollector
) {
    @GetMapping("/open")
    fun openReleases() = releaseCollector.openReleases()

    @GetMapping("/completed")
    fun completedReleases() = releaseCollector.completedReleases()
}