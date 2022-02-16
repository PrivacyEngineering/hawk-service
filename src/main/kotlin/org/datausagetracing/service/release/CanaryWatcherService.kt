package org.datausagetracing.service.release

import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import io.fabric8.kubernetes.client.WatcherException
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Service
@Profile("flagger-canary")
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
class CanaryWatcherService(
    private val kubernetesClient: KubernetesClient,
    private val releaseCollector: ReleaseCollector
) : Watcher<Canary> {

    @PostConstruct
    fun initialize() {
        val canaries = kubernetesClient
            .customResources(Canary::class.java, CanaryList::class.java)
        canaries
            .list()
            .items
            .map(Canary::createRelease)
            .forEach(releaseCollector::startRelease)
        canaries.watch(this)
    }

    @Scheduled(initialDelay = 5_000L)
    private fun initializeWithDelay() = initialize()

    override fun eventReceived(action: Watcher.Action, canary: Canary) = when (action) {
        Watcher.Action.ADDED -> releaseCollector.startRelease(canary.createRelease())
        Watcher.Action.DELETED -> releaseCollector
            .endRelease(canary.createIdentifier(), LocalDateTime.now())

        else -> Unit
    }

    override fun onClose(cause: WatcherException) = initializeWithDelay()
}

fun Canary.createIdentifier() = TargetIdentifier(
    metadata.namespace,
    spec.targetRef.apiVersion,
    spec.targetRef.kind,
    spec.targetRef.name
)

fun Canary.createRelease() = Release(
    createIdentifier(),
    "canary",
    metadata.name,
    LocalDateTime.now()
)