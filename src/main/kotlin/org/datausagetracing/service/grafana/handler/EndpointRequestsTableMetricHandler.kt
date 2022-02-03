package org.datausagetracing.service.grafana.handler

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointRequestsTableMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "endpoint_requests_table"

    override fun query(request: QueryRequest): List<QueryResult> {
        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("endpoint", "string"),
                    TableColumn("requests", "number")
                ),
                usageRepository.countEndpointRequests(request.from()!!, request.to()!!)
                    .map {
                        arrayOf(
                            it.endpoint as Any,
                            it.count as Any
                        )
                    }
            )
        )
    }
}