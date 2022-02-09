package org.datausagetracing.service.grafana.handler.table

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class ServiceInitiatorRequestsTableMetricHandler(val usageRepository: UsageRepository) :
    MetricHandler {
    override val target: String = "service_initiator_requests_table"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("service", "string"),
                    TableColumn("initiator", "string"),
                    TableColumn("requests", "number")
                ),
                usageRepository.countServiceInitiatorRequests(request.from()!!, request.to()!!)
                    .map {
                        arrayOf(
                            it.service as Any,
                            it.initiator as Any,
                            it.count as Any
                        )
                    }
            )
        )
    }
}