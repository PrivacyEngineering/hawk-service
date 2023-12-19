package io.hawk.service.traffic.grafana.handler.table

import io.hawk.service.traffic.grafana.*
import io.hawk.service.traffic.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointRequestsTableMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "endpoint_requests_table"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
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