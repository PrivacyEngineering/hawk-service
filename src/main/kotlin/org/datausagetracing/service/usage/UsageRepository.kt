package org.datausagetracing.service.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UsageRepository : JpaRepository<Usage, UUID> {
    fun findAllByIdIn(ids: List<UUID>): List<Usage>

    @Query("select usage.endpointId from Usage usage where usage.mapping is null group by usage.endpointId")
    fun findUnmappedEndpoints(): List<String>

    @Query("select new org.datausagetracing.service.usage.ServiceRequestCount(usage.endpointHost, usage.initiatorHost, count(usage)) from Usage usage group by usage.endpointHost, usage.initiatorHost")
    fun countServiceRequests(): List<ServiceRequestCount>
}

data class ServiceRequestCount(
    val endpointHost: String,
    val initiatorHost: String,
    val count: Long
)