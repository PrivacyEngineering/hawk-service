package org.datausagetracing.service.grafana

class AdhocValueRequest {
    var key: String? = null
}

data class AdhocValueResult(val text: String)