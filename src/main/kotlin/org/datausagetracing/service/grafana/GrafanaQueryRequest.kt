package org.datausagetracing.service.grafana

class QueryRequest {
    var panelId: String? = null
    var range: QueryRange? = null
    var intervalMs: Int? = null
    var maxDataPoints: Int? = null
    var targets: MutableList<QueryTarget> = mutableListOf()
}

class QueryRange {
    var from: String? = null
    var to: String? = null
}

class QueryTarget {
    lateinit var target: String
    var refId: String? = null
    var type: String? = null
    var data: Any? = null
}