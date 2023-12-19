package io.hawk.service.module.cluster

import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.client.KubernetesClient
import io.fabric8.kubernetes.client.Watcher
import io.hawk.service.module.ReconnectingWatcher
import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.stereotype.Component

@Component
@ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
class PodWatcher(
    private val kubernetesClient: KubernetesClient,
    private val clusterCollector: ClusterCollector
) : ReconnectingWatcher<Pod>() {
    @PostConstruct
    override fun initialize() {
        kubernetesClient.pods().watch(this)
    }

    override fun eventReceived(action: Watcher.Action, pod: Pod) {
        when (action) {
            Watcher.Action.ADDED -> clusterCollector.registerPod(pod)
            Watcher.Action.DELETED -> clusterCollector.unregisterPod(pod)
            else -> {}
        }
    }
}