package org.datausagetracing.service.grafana.handler.table

import org.datausagetracing.service.field.ExplicitFieldRepository
import org.datausagetracing.service.grafana.*
import org.springframework.stereotype.Service

@Service
class FieldRequestsTableMetricHandler(
    private val fieldRepository: ExplicitFieldRepository
) : MetricHandler {
    override val target = "field_requests"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        val fields = target.payload("fields") as? List<String> ?: return emptyList()

        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("field", "string"),
                    TableColumn("requests", "number")
                ),
                fieldRepository.fieldRequests(fields.toTypedArray()).map {
                    arrayOf(it.key, it.value)
                }
            )
        )
    }
}