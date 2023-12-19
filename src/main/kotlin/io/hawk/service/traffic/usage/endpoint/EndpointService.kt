package io.hawk.service.traffic.usage.endpoint

import io.hawk.service.traffic.usage.GroupedUsageField
import io.hawk.service.traffic.usage.UsageFieldRepository
import io.hawk.service.traffic.usage.UsageRepository
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