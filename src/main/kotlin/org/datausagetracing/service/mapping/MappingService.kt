package org.datausagetracing.service.mapping

import org.datausagetracing.service.field.FieldRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MappingService(
    val mappingRepository: MappingRepository,
    val mappingFieldRepository: MappingFieldRepository,
    val fieldRepository: FieldRepository
) {
    fun listMappings(): List<Mapping> = mappingRepository.findAll()

    fun showMapping(id: Int) =
        mappingRepository.findByIdOrNull(id) ?: error("Mapping does not exist")

    @Transactional
    fun insertMapping(request: MappingInsertRequest) {
        val foundFields = fieldRepository.findAllByNameIn(
            request.fields.map(MappingFieldInsertRequest::field).distinct()
        ).associateBy { it.name }

        val mapping = Mapping()
        mapping.endpointId = request.endpointId
        mappingRepository.saveAndFlush(mapping)

        mapping.fields = request.fields.mapNotNull {
            val mappingField = MappingField()
            mappingField.field = foundFields[it.field] ?: return@mapNotNull null
            mappingField.phase = it.phase
            mappingField.namespace = it.namespace
            mappingField.format = it.format
            mappingField.path = it.path
            mappingField.mapping = mapping
            mappingField
        }.let(mappingFieldRepository::saveAllAndFlush)
    }

    @Transactional
    fun updateMapping(request: MappingUpdateRequest) {
        val mapping = mappingRepository.findByEndpointId(request.endpointId)
            ?: error("Mapping with endpoint id ${request.endpointId} does not exist")

        val requestFields = request.fields.associateBy(MappingFieldUpdateRequest::id)

        mapping.fields.forEach {
            val requestField = requestFields[it.id] ?: run {
                mappingFieldRepository.delete(it)
                return@forEach
            }
            var update = false

            if(it.phase != requestField.phase) {
                it.phase = requestField.phase
                update = true
            }

            if(it.namespace != requestField.namespace) {
                it.namespace = requestField.namespace
                update = true
            }

            if(it.format != requestField.format) {
                it.format = requestField.format
                update = true
            }

            if(it.path != requestField.path) {
                it.path = requestField.path
                update = true
            }

            if(update) mappingFieldRepository.save(it)
        }

        val newFieldIds = (requestFields.keys - mapping.fields.map(MappingField::id))
        val newFieldNames = newFieldIds.mapNotNull { requestFields[it]?.field }.distinct()
        val foundFields = fieldRepository
            .findAllByNameIn(newFieldNames)
            .associateBy { it.name }

        newFieldIds.mapNotNull {
            val requestField = requestFields[it] ?: return@mapNotNull null
            val mappingField = MappingField()
            mappingField.field = foundFields[requestField.field] ?: return@mapNotNull null
            mappingField.phase = requestField.phase
            mappingField.namespace = requestField.namespace
            mappingField.format = requestField.format
            mappingField.path = requestField.path
            mappingField.mapping = mapping
            mappingField
        }.let(mappingFieldRepository::saveAllAndFlush)
    }

    fun deleteMapping(id: Int) {
        mappingRepository.deleteById(id)
    }
}