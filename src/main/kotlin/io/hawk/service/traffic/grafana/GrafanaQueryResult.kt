package io.hawk.service.traffic.grafana

interface QueryResult

data class TimeSeriesQueryResult(
    val target: String,
    val datapoints: List<Array<Long>>
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