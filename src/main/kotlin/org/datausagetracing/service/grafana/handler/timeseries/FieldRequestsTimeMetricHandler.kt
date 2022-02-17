package org.datausagetracing.service.grafana.handler.timeseries

import org.datausagetracing.service.field.ExplicitFieldRepository
import org.datausagetracing.service.grafana.*
import org.springframework.stereotype.Service

@Service
class FieldRequestsTimeMetricHandler(
    private val fieldRepository: ExplicitFieldRepository
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
            )
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