package io.hawk.service.module.release

import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import io.hawk.service.module.ReconnectingWatcher
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Profile("flagger-canary")
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
class CanaryWatcher(
    private val kubernetesClient: KubernetesClient,
    private val releaseCollector: ReleaseCollector
) : ReconnectingWatcher<Canary>() {

    @PostConstruct
    override fun initialize() {
        val canaries = kubernetesClient
            .resources(Canary::class.java)
        canaries
            .list()
            .items
            .map(Canary::createRelease)
            .forEach(releaseCollector::startRelease)
        canaries.watch(this)
    }

    override fun eventReceived(action: Watcher.Action, canary: Canary) = when (action) {
        Watcher.Action.ADDED -> releaseCollector.startRelease(canary.createRelease())
        Watcher.Action.DELETED -> releaseCollector
            .endRelease(canary.createIdentifier(), LocalDateTime.now())

        else -> Unit
    }
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