package org.datausagetracing.service.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UsageFieldRepository : JpaRepository<UsageField, UUID> {
    @Query("select new org.datausagetracing.service.usage.GroupedUsageField(field.phase, field.namespace, field.format, field.path) from UsageField field where field.usage.endpointId = :endpointId group by field.phase, field.namespace, field.format, field.path")
    fun findGroupedUsageFields(endpointId: String): List<GroupedUsageField>
}

data class GroupedUsageField(
    val phase: String,
    val namespace: String,
    val format: String,
    val path: String
)