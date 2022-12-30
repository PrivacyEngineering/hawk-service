package io.hawk.service.traffic.release

import io.fabric8.kubernetes.client.DefaultKubernetesClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform
import org.springframework.boot.cloud.CloudPlatform
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KubernetesConfiguration {
    @Bean
    @ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
    fun kubernetesClient() = DefaultKubernetesClient()
}