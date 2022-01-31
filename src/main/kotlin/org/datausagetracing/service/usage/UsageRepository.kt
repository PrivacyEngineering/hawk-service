package org.datausagetracing.service.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UsageRepository : JpaRepository<Usage, UUID> {
    fun findAllByIdIn(ids: List<UUID>): List<Usage>

    @Query("select distinct usage.endpointId from Usage usage where usage.mappings is empty")
    fun findUnmappedEndpoints(): List<String>


}