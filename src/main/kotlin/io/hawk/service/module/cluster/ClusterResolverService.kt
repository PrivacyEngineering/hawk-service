package io.hawk.service.module.cluster

import io.fabric8.kubernetes.client.KubernetesClient
import org.springframework.stereotype.Service

@Service
class ClusterResolverService(
    private val kubernetesClient: KubernetesClient
) {

}