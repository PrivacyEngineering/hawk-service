package org.datausagetracing.service.grafana

interface MetricHandler {
    val target: String

    /**
     * List of Grafana AdHoc Filter Keys that this MetricHandler specifies.
     * The key is tag name, value is tag type.
     */
    val adhocKeys: Map<String, String> get() = emptyMap()

    fun search(): List<String> = emptyList()

    fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> = emptyList()

    fun adhocValues(key: String): List<String> = emptyList()
}