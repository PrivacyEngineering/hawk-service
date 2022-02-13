package org.datausagetracing.service.release

import io.fabric8.kubernetes.api.model.KubernetesResource
import io.fabric8.kubernetes.api.model.Namespaced
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.client.CustomResourceList
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version

@Version("v1beta1")
@Group("flagger.app")
class Canary: CustomResource<CanarySpec, Nothing>(), Namespaced

class CanaryList: CustomResourceList<Canary>()

class CanarySpec : KubernetesResource {
    lateinit var targetRef: TargetRef
}

class TargetRef {
    lateinit var apiVersion: String

    lateinit var kind: String

    lateinit var name: String
}