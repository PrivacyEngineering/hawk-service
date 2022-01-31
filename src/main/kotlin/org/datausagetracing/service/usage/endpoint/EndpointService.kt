package org.datausagetracing.service.usage.endpoint

import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointService(val usageRepository: UsageRepository) {
    fun fetchUnmappedEndpoints(): List<String> {
        return usageRepository.findUnmappedEndpoints()
//            .filterNot { it.endpointId == null || it.endpointId!!.isEmpty() }
//            .map {
//                Endpoint(
//                    it.endpointId!!,
//                    it.endpointHost,
//                    it.endpointProtocol,
//                    it.endpointProperties.toMap()
//                )
//            }
    }
}