package org.datausagetracing.service.grafana.handler.table

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointInitiatorRequestsTableMetricHandler(val usageRepository: UsageRepository) :
    MetricHandler {
    override val target: String = "endpoint_initiator_requests_table"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("endpoint", "string"),
                    TableColumn("initiator", "string"),
                    TableColumn("requests", "number")
                ),
                usageRepository.countEndpointInitiatorRequests(request.from()!!, request.to()!!)
                    .map {
                        arrayOf(
                            it.endpoint as Any,
                            it.initiator as Any,
                            it.count as Any
                        )
                    }
            )
        )
    }
}