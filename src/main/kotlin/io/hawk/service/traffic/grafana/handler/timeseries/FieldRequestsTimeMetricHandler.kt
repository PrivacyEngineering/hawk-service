package io.hawk.service.traffic.grafana.handler.timeseries

import io.hawk.service.traffic.field.ExplicitFieldRepository
import io.hawk.service.traffic.grafana.*
import org.springframework.stereotype.Service

@Service
class FieldRequestsTimeMetricHandler(
    private val fieldRepository: io.hawk.service.traffic.field.ExplicitFieldRepository
) : MetricHandler {
    override val target = "field_requests_time"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        val fields = target.payload("fields") as? List<String> ?: return emptyList()
        if(fields.isEmpty()) return emptyList()

        return listOf(TimeSeriesQueryResult(
            "requests",
            fieldRepository.fieldRequests(
                request.from()!!,
                request.to()!!,
                request.intervalMs,
                fields.toTypedArray()
            ).toSortedMap()
                .map {
                    arrayOf(
                        it.value,
                        it.key
                    )
                }
        )
        )
    }
}