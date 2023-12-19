package io.hawk.service.traffic.mapping

import org.springframework.data.jpa.repository.JpaRepository

interface MappingRepository : JpaRepository<Mapping, Int> {
    fun findByEndpointId(endpointId: String): Mapping?
}