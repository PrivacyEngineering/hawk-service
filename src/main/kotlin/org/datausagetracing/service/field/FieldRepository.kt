package org.datausagetracing.service.field

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface FieldRepository : JpaRepository<Field, Int> {
    fun findByName(name: String): Field?

    fun existsByName(name: String): Boolean

    @Transactional
    fun deleteByName(name: String)

    fun findAllByNameIn(names: List<String>): List<Field>

    @Query("select sum(usageField.count) from MappingField mappingField left join mappingField.usageFields usageField where mappingField.field.name = ?1")
    fun sumFieldRequests(fieldName: String): Long
}