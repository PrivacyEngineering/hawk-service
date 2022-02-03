package org.datausagetracing.service.grafana.handler

import org.datausagetracing.service.grafana.MetricHandler
import org.datausagetracing.service.grafana.QueryRequest
import org.datausagetracing.service.grafana.QueryResult
import org.datausagetracing.service.grafana.TimeSeriesQueryResult
import org.datausagetracing.service.usage.ExplicitUsageRepository
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class RequestsTimeMetricHandler(
    private val usageRepository: UsageRepository,
    private val explicitUsageRepository: ExplicitUsageRepository
) : MetricHandler {
    override val target = "requests_time"

    override fun query(request: QueryRequest): List<QueryResult> {
        val endpoint = request.findFilter("endpointId")

        if (endpoint != null) {
            return listOf(TimeSeriesQueryResult(
                "requests",
                explicitUsageRepository.endpointResultsTimeSeries(
                    request.from()!!,
                    request.to()!!,
                    request.intervalMs,
                    endpoint
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

        return listOf(
            TimeSeriesQueryResult(
                "requests",
                explicitUsageRepository.resultsTimeSeries(
                    request.from()!!,
                    request.to()!!,
                    request.intervalMs
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

    override val adhocKeys: Map<String, String> = mapOf("endpointId" to "string")

    override fun adhocValues(key: String): List<String> {
        if (key == "endpointId") {
            return usageRepository.findEndpoints()
        }
        return emptyList()
    }
}