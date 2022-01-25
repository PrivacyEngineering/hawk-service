package org.datausagetracing.service.usage.insert

import org.datausagetracing.service.usage.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UsageInsertService(
    private val usageRepository: UsageRepository,
    private val fieldRepository: FieldRepository,
    private val tagRepository: TagRepository,
    private val endpointPropertyRepository: EndpointPropertyRepository,
    private val initiatorPropertyRepository: InitiatorPropertyRepository
) {
    @Transactional
    fun insertUsage(request: UsageRequest) {
        // Find existing Usage or create new one.
        val usage = usageRepository.findByIdOrNull(request.uuid()) ?: Usage()
        doInsertUsage(usage, listOf(request))
    }

    @Transactional
    fun insertUsages(requests: List<UsageRequest>) {
        // Find all existing Usages of the list of requests.
        val existingUsages = usageRepository
            .findAllByIdIn(requests.map(UsageRequest::uuid))
            .associateBy(Usage::id)

        // Aggregate Usage Request batch and insert them
        requests
            // Sort by time, to prevent wrong overriding
            .sortedBy { it.metadata.timestamp }
            // Group by uuid to identify requests with same uuid
            .groupBy { it.uuid() }
            .forEach { (id, requests) ->
                // Find existing Usage or create new one.
                val usage = existingUsages[id] ?: Usage()
                // Insert usage with all belonging modification requests
                doInsertUsage(usage, requests)
            }
    }

    private fun doInsertUsage(usage: Usage, requests: List<UsageRequest>) {
        // Apply overriding of all Usage base properties e.g. timestamps etc.
        requests.forEach { usage.apply(it) }
        // Save the Usage itself
        usageRepository.save(usage)

        // Insert all entities belonging to the Usage
        // Usage must exist first because of foreign key constraint
        requests.forEach {
            it.insertFields(usage)
            it.insertEndpointProperties(usage)
            it.insertInitiatorProperties(usage)
            it.insertTags(usage)
        }
    }

    private fun UsageRequest.insertFields(usage: Usage) {
        fieldRepository.saveAll(fields.map {
            Field().apply {
                id = UUID.randomUUID()
                phase = metadata.phase.name
                side = metadata.side.name
                namespace = it.namespace
                format = it.format
                path = it.path
                count = it.count
                this.usage = usage
            }
        })
    }

    private fun UsageRequest.insertEndpointProperties(usage: Usage) {
        endpointPropertyRepository.saveAll(endpoint.additionalProperties.map {
            EndpointProperty().apply {
                id = UUID.randomUUID()
                phase = metadata.phase.name
                side = metadata.side.name
                key = it.key
                value = it.value
                this.usage = usage
            }
        })
    }

    private fun UsageRequest.insertInitiatorProperties(usage: Usage) {
        initiatorPropertyRepository.saveAll(initiator.additionalProperties.map {
            InitiatorProperty().apply {
                id = UUID.randomUUID()
                phase = metadata.phase.name
                side = metadata.side.name
                key = it.key
                value = it.value
                this.usage = usage
            }
        })
    }

    private fun UsageRequest.insertTags(usage: Usage) {
        tagRepository.saveAll(tags.tags.map {
            Tag().apply {
                id = UUID.randomUUID()
                phase = metadata.phase.name
                side = metadata.side.name
                key = it.key
                value = it.value
                this.usage = usage
            }
        })
    }

    private fun Usage.apply(request: UsageRequest) {
        id = request.uuid()
        request.metadata.apply {
            if (side == Side.CLIENT && phase == Phase.REQUEST) clientRequestTimestamp = timestamp
            if (side == Side.CLIENT && phase == Phase.RESPONSE) clientResponseTimestamp = timestamp
            if (side == Side.SERVER && phase == Phase.REQUEST) serverRequestTimestamp = timestamp
            if (side == Side.SERVER && phase == Phase.RESPONSE) serverResponseTimestamp = timestamp
        }
        request.endpoint.id?.also { endpointId = it }
        request.endpoint.host?.also { endpointHost = it }
        request.endpoint.protocol?.also { endpointProtocol = it }
        request.initiator.host?.also { initiatorHost = it }
    }
}