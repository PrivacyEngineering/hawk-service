package org.datausagetracing.service.usage

import org.springframework.stereotype.Service
import java.util.*

@Service
class UsageService(
    private val usageRepository: UsageRepository,
    private val fieldRepository: FieldRepository
) {

    fun insertUsage(request: UsageRequest) {
        Usage().apply {
            id = request.id()
            when (request.metadata.side) {
                Side.CLIENT -> when (request.metadata.phase) {
                    Phase.REQUEST -> clientRequestTimestamp = request.metadata.timestamp
                    Phase.RESPONSE -> clientResponseTimestamp = request.metadata.timestamp
                }
                Side.SERVER -> when (request.metadata.phase) {
                    Phase.REQUEST -> serverRequestTimestamp = request.metadata.timestamp
                    Phase.RESPONSE -> serverResponseTimestamp = request.metadata.timestamp
                }
            }
            request.endpoint.id?.also { endpointId = it }
            request.endpoint.host?.also { endpointHost = it }
            request.endpoint.protocol?.also { endpointProtocol = it }
            endpointAdditionalProperties = request.endpoint.additionalProperties

            request.initiator.host?.also { initiatorHost = it }
            initiatorAdditionalProperties = request.initiator.additionalProperties

            fields.addAll(request.fields.map {
                Field().apply {
                    id = UUID.randomUUID()
                    namespace = it.namespace
                    format = it.path
                    count = it.count
                }
            })
            fieldRepository.saveAll(fields)

            tags = request.tags.tags
        }.also(usageRepository::save)
    }

}