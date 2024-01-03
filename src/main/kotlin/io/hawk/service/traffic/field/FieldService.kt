package io.hawk.service.traffic.field

import io.hawk.dlp.common.InfoType
import org.springframework.stereotype.Service

@Service
class FieldService(
    private val fieldRepository: FieldRepository
) {
    fun listFields(): List<Field> = fieldRepository.findAll()

    fun showField(name: String): Field = fieldRepository.findByName(name) ?: error("Field does not exist")

    fun insertField(request: FieldRequest) {
        val field = Field()
        field.apply(request)
        fieldRepository.save(field)
    }

    fun updateField(request: FieldRequest) {
        val field = fieldRepository.findByName(request.name.lowercase())
            ?: error("Can not find existing request named ${request.name.lowercase()}")
        field.apply(request)
        fieldRepository.save(field)
    }

    fun deleteField(name: String) {
        fieldRepository.deleteByName(name.lowercase())
    }

    private fun Field.apply(request: FieldRequest) {
        name = request.name.lowercase()

        request.description?.also { description = it }
        infoTypes = request.infoTypes.map { InfoType.valueOf(it.uppercase()) }.toTypedArray()
        request.personalData?.also { personalData = it }
        request.specialCategoryPersonalData?.also { specialCategoryPersonalData = it }
        legalBases = request.legalBases
        request.legalRequirement?.also { legalRequirement = it }
        request.contractualRegulation?.also { contractualRegulation = it }
        request.obligationToProvide?.also { obligationToProvide = it }
        request.consequences?.also { consequences = it }
    }
}