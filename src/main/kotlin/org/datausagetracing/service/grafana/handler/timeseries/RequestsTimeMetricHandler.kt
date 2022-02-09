package org.datausagetracing.service.grafana.handler.timeseries

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.usage.ExplicitUsageRepository
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class RequestsTimeMetricHandler(
    private val usageRepository: UsageRepository,
    private val explicitUsageRepository: ExplicitUsageRepository
) : MetricHandler {
    override val target = "requests_time"

    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        val endpoint = target.payload("endpoint") as? String

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
}