package io.hawk.service.traffic.grafana.handler.table

import io.hawk.service.traffic.field.ExplicitFieldRepository
import io.hawk.service.traffic.grafana.*
import org.springframework.stereotype.Service

@Service
class FieldRequestsTableMetricHandler(
    private val fieldRepository: io.hawk.service.traffic.field.ExplicitFieldRepository
) : MetricHandler {
    override val target = "field_requests"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        val fields = target.payload("fields") as? List<String> ?: return emptyList()
        if(fields.isEmpty()) return emptyList();

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