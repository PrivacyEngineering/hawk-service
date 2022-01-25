package org.datausagetracing.service.field

import org.springframework.data.jpa.repository.JpaRepository

interface FieldRepository : JpaRepository<Field, Int> {
    fun findByName(name: String): Field?

    fun existsByName(name: String): Boolean

    fun deleteByName(name: String)
}