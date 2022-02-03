package org.datausagetracing.service.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.ZonedDateTime
import java.util.*

const val packagePrefix: String = "org.datausagetracing.service.usage"

interface UsageRepository : JpaRepository<Usage, UUID> {
    fun findAllByIdIn(ids: List<UUID>): List<Usage>

    @Query("select usage.endpointId from Usage usage group by usage.endpointId")
    fun findEndpoints(): List<String>

    @Query("select usage.endpointId from Usage usage where usage.mapping is null group by usage.endpointId")
    fun findUnmappedEndpoints(): List<String>

    @Query("select new ${packagePrefix}.ServiceInitiatorRequestCount(usage.endpointHost, usage.initiatorHost, count(usage)) from Usage usage where (usage.clientRequestTimestamp between :from and :to) or (usage.serverRequestTimestamp between :from and :to) group by usage.endpointHost, usage.initiatorHost")
    fun countServiceInitiatorRequests(from: ZonedDateTime, to: ZonedDateTime): List<ServiceInitiatorRequestCount>

    @Query("select new ${packagePrefix}.ServiceRequestCount(usage.endpointHost, count(usage)) from Usage usage where (usage.clientRequestTimestamp between :from and :to) or (usage.serverRequestTimestamp between :from and :to) group by usage.endpointHost")
    fun countServiceRequests(from: ZonedDateTime, to: ZonedDateTime): List<ServiceRequestCount>

    @Query("select new ${packagePrefix}.EndpointRequestCount(usage.endpointId, count(usage)) from Usage usage where (usage.clientRequestTimestamp between :from and :to) or (usage.serverRequestTimestamp between :from and :to) group by usage.endpointId")
    fun countEndpointRequests(from: ZonedDateTime, to: ZonedDateTime): List<EndpointRequestCount>

    @Query("select new ${packagePrefix}.EndpointInitiatorRequestCount(usage.endpointId, usage.initiatorHost, count(usage)) from Usage usage where (usage.clientRequestTimestamp between :from and :to) or (usage.serverRequestTimestamp between :from and :to) group by usage.endpointId, usage.initiatorHost")
    fun countEndpointInitiatorRequests(from: ZonedDateTime, to: ZonedDateTime): List<EndpointInitiatorRequestCount>
}

data class ServiceRequestCount(
    val service: String,
    val count: Long
)

data class ServiceInitiatorRequestCount(
    val service: String,
    val initiator: String,
    val count: Long
)

data class EndpointRequestCount(
    val endpoint: String,
    val count: Long
)

data class EndpointInitiatorRequestCount(
    val endpoint: String,
    val initiator: String,
    val count: Long
)