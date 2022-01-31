package org.datausagetracing.service.grafana

interface MetricHandler {
    val target: String

    fun query(request: QueryRequest): List<QueryResult>
}