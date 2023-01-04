package io.hawk.service.module.cluster

import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.Service
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class ClusterCollector {
    // TODO: add support for service meshes (VirtualService etc.)
    private val services = ConcurrentHashMap.newKeySet<Service>()
    private val pods = ConcurrentHashMap.newKeySet<Pod>()

    fun resolveAppName(rawName: String): String? {
        val namespace = rawName.substringBefore('.', "")
        val name = rawName.substringAfter('.')

        val result = (services + pods)
            .filter { namespace.isEmpty() || it.metadata.namespace == namespace }
            // TODO: add levenshtein distance comparison
            .firstOrNull { it.metadata.name.startsWith(name) }

        return result?.metadata?.labels?.get("hawk.io/app-name")
    }

    fun registerPod(pod: Pod) = pods.add(pod)

    fun unregisterPod(pod: Pod) = pods.remove(pod)

    fun registerService(service: Service) = services.add(service)

    fun unregisterService(service: Service) = services.remove(service)
}