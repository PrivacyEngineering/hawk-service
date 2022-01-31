package org.datausagetracing.service.grafana

interface QueryResult

data class TimeSeriesQueryResult(
    val target: String,
    val datapoints: List<Array<Int>>
) : QueryResult

data class TableQueryResult(
    val columns: List<TableColumn>,
    val rows: List<Array<Any>>
) : QueryResult {
    val type: String = "table"
}

data class TableColumn(
    val text: String,
    val type: String
)