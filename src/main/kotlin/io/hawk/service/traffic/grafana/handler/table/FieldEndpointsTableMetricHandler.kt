package io.hawk.service.traffic.grafana.handler.table

import io.hawk.service.traffic.field.ExplicitFieldRepository
import io.hawk.service.traffic.grafana.*
import org.springframework.stereotype.Service

@Service
class FieldEndpointsTableMetricHandler(
    private val fieldRepository: io.hawk.service.traffic.field.ExplicitFieldRepository
) : MetricHandler {
    override val target = "field_endpoints"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        val field = target.payload("field") as? String
        if (field == null || field.isEmpty()) return emptyList()

        return listOf(
            TableQueryResult(
                listOf(
                    TableColumn("endpoint", "string")
                ),
                fieldRepository.fieldEndpoints(field).map { arrayOf(it) }
            )
        )
    }
}