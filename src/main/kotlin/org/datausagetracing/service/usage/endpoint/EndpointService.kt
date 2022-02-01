package org.datausagetracing.service.usage.endpoint

import org.datausagetracing.service.usage.GroupedUsageField
import org.datausagetracing.service.usage.UsageFieldRepository
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointService(
    val usageRepository: UsageRepository,
    val usageFieldRepository: UsageFieldRepository
) {
    fun fetchUnmappedEndpoints(): List<String> {
        return usageRepository.findUnmappedEndpoints()
    }

    fun fetchGroupedFields(endpointId: String): List<GroupedUsageField> {
        return usageFieldRepository.findGroupedUsageFields(endpointId)
    }
}