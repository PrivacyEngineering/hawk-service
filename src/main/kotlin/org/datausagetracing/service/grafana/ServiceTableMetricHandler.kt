package org.datausagetracing.service.grafana

import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class ServiceTableMetricHandler(val usageRepository: UsageRepository) : MetricHandler {
    override val target: String = "service_graph_table"

    override fun query(request: QueryRequest): List<QueryResult> {
        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("target_app", "string"),
                    TableColumn("app", "string"),
                    TableColumn("req_rate", "number")
                ),
                usageRepository.countServiceRequests()
                    .map {
                        arrayOf(
                            it.endpointHost as Any,
                            it.initiatorHost as Any,
                            it.count as Any
                        )
                    }
            )
        )
    }
}