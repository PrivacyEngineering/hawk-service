package io.hawk.service.traffic.grafana.handler.search

import io.hawk.service.traffic.grafana.MetricHandler
import io.hawk.service.traffic.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class EndpointsSearchMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "endpoints"

    override fun search(): List<String> = usageRepository.findEndpoints()
}