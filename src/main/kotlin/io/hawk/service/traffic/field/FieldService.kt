package io.hawk.service.traffic.field

import io.hawk.dlp.common.InfoType
import io.hawk.service.dlp.DlpFinding
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service

@Service
class FieldService(
    private val fieldRepository: FieldRepository,
    private val entityManager: EntityManager
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

    fun showDlp(fieldName: String): List<DlpFinding> {
        return entityManager
                .createNativeQuery("""
                    select d.id, d.info_type, d.likelihood, d.additional, d.occurrences, d.result_id from dlp_finding d join field on d.info_type = ANY(field.info_types) AND field.name = :field_name
                """.trimIndent(), DlpFinding::class.java)
                .setParameter("field_name", fieldName)
                .resultList as List<DlpFinding>
    }
}