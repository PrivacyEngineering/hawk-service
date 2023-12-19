package io.hawk.service.traffic.grafana.handler.table

import io.hawk.service.traffic.grafana.*
import io.hawk.service.traffic.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class ServiceRequestsTableMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "service_requests_table"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
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