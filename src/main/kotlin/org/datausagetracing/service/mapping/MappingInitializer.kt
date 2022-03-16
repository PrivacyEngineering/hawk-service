package org.datausagetracing.service.mapping

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.logging.log4j.LogManager
import org.datausagetracing.service.field.FieldInitializer
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class MappingInitializer(
    val mappingService: MappingService,
    val mappingRepository: MappingRepository,
    val mappingConfiguration: MappingConfiguration,
    fieldInitializer: FieldInitializer // Load fields before
) {
    private val logger = LogManager.getLogger(javaClass.name)

    @PostConstruct
    fun initialize() {
        logger.info("Starting importing Mappings...")
        val jsonMappings = mappingConfiguration.json
            ?.takeUnless { it.isEmpty() }
            ?.let { ObjectMapper().readValue<List<MappingInsertRequest>>(it) }
            ?: emptyList()
        val distinctMappings = (jsonMappings + mappingConfiguration.mappings)
            .distinctBy { it.endpointId }
        logger.info("Found ${distinctMappings.size} different mappings")
        distinctMappings.forEach(this::save)
        logger.info("Mappings imported!")
    }

    fun save(request: MappingInsertRequest) {
        logger.debug("Import mapping $request")
        mappingRepository.findByEndpointId(request.endpointId)?.also { mapping ->
            val updateRequest = MappingUpdateRequest()
            updateRequest.endpointId = mapping.endpointId
            updateRequest.fields = request.fields.map {
                val mappingField = mapping.fields.firstOrNull { requestField ->
                    requestField.field.name == it.field &&
                            requestField.phase == it.phase &&
                            requestField.namespace == it.namespace &&
                            requestField.format == it.format &&
                            requestField.path == it.path
                }
                MappingFieldUpdateRequest().apply {
                    id = mappingField?.id
                    phase = it.phase
                    namespace = it.namespace
                    format = it.format
                    path = it.path
                }
            }.toMutableList()
            mappingService.updateMapping(updateRequest)

            return@save
        }
        mappingService.insertMapping(request)
    }
}