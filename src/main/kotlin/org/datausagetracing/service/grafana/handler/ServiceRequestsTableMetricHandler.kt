package org.datausagetracing.service.grafana.handler

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class ServiceRequestsTableMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "service_requests_table"

    override fun query(request: QueryRequest): List<QueryResult> {
        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("service", "string"),
                    TableColumn("requests", "number")
                ),
                usageRepository.countServiceRequests(request.from()!!, request.to()!!)
                    .map {
                        arrayOf(
                            it.service as Any,
                            it.count as Any
                        )
                    }
            )
        )
    }
}