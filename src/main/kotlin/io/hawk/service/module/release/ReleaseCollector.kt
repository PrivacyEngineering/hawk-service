package io.hawk.service.module.release

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class ReleaseCollector(
    private val eventPublisher: ApplicationEventPublisher
) {
    val releases = ConcurrentHashMap<TargetIdentifier, Release>()

    fun startRelease(release: Release) {
        releases[release.target] = release
        eventPublisher.publishEvent(StartReleaseEvent(release))
    }

    fun endRelease(target: TargetIdentifier, end: LocalDateTime) {
        releases.compute(target) { _, release -> release?.copy(end = end) }
        eventPublisher.publishEvent(EndReleaseEvent(releases[target] ?: return))
    }

    fun openReleases(): List<Release> = releases
        .values
        .filter { it.end == null }
        .map { it.copy() }

    fun completedReleases(): List<Release>  = releases
        .values
        .filter { it.end != null }
        .map { it.copy() }
}

abstract class ReleaseEvent(release: Release): ApplicationEvent(release)

class StartReleaseEvent(release: Release): ReleaseEvent(release)

class EndReleaseEvent(release: Release): ReleaseEvent(release)