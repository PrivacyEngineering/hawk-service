package io.hawk.service.module.cluster

import io.fabric8.kubernetes.api.model.Service
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
class ServiceWatcher(
    private val kubernetesClient: KubernetesClient,
    private val clusterCollector: ClusterCollector
) : ReconnectingWatcher<Service>() {
    private val logger = LogManager.getLogger(javaClass)

    @PostConstruct
    override fun initialize() {
        try {
            kubernetesClient.services().watch(this)
        } catch (e: Exception) {
            logger.error("Watching kubernetes services failed", e)
        }
    }

    override fun eventReceived(action: Watcher.Action, service: Service) {
        when (action) {
            Watcher.Action.ADDED -> clusterCollector.registerService(service)
            Watcher.Action.DELETED -> clusterCollector.unregisterService(service)
            else -> {}
        }
    }
}