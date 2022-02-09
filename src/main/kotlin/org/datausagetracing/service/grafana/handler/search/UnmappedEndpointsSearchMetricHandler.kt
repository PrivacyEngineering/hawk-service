package org.datausagetracing.service.grafana.handler.search

import org.datausagetracing.service.grafana.MetricHandler
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class UnmappedEndpointsSearchMetricHandler(
    private val usageRepository: UsageRepository
): MetricHandler {
    override val target = "unmapped_endpoints"

    override fun search(): List<String> = usageRepository.findUnmappedEndpoints()
}