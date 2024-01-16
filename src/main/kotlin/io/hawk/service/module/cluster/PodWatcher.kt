package io.hawk.service.module.cluster

import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import io.hawk.service.module.ReconnectingWatcher
import jakarta.annotation.PostConstruct
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.stereotype.Component

@Component
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
class PodWatcher(
    private val kubernetesClient: KubernetesClient,
    private val clusterCollector: ClusterCollector
) : ReconnectingWatcher<Pod>() {
    private val logger = LogManager.getLogger(javaClass)

    @PostConstruct
    override fun initialize() {
        try {
            kubernetesClient.pods().watch(this)
        } catch (e: Exception) {
            logger.error("Watching kubernetes pods failed", e)
        }
    }

    override fun eventReceived(action: Watcher.Action, pod: Pod) {
        when (action) {
            Watcher.Action.ADDED -> clusterCollector.registerPod(pod)
            Watcher.Action.DELETED -> clusterCollector.unregisterPod(pod)
            else -> {}
        }
    }
}