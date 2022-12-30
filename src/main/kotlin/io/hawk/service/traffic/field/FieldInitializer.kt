package io.hawk.service.traffic.field

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import jakarta.annotation.PostConstruct

@Service
class FieldInitializer(
    val fieldService: FieldService,
    val fieldRepository: FieldRepository,
    val fieldConfiguration: FieldConfiguration,
) {
    private val logger = LogManager.getLogger(javaClass.name)

    @PostConstruct
    fun initialize() {
        logger.info("Starting importing Fields...")
        val jsonMappings = fieldConfiguration.json
            ?.takeUnless { it.isEmpty() }
            ?.let { ObjectMapper().readValue<List<FieldRequest>>(it) }
            ?: emptyList()
        val distinctMappings = (jsonMappings + fieldConfiguration.fields)
            .distinctBy { it.name }
        logger.info("Found ${distinctMappings.size} different fields")
        distinctMappings.forEach(this::save)
        logger.info("Fields imported!")
    }

    fun save(request: FieldRequest) {
        logger.debug("Import field $request")
        fieldRepository.findByName(request.name)?.also { field ->
            if(!request.equalsField(field)) {
                fieldService.updateField(request)
            }
            return@save
        }
        fieldService.insertField(request)
    }
}