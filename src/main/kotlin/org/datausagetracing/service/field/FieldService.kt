package org.datausagetracing.service.field

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
        if (request.description != null) description = request.description
        personalData = request.personalData
        specialCategoryPersonalData = request.specialCategoryPersonalData
    }
}