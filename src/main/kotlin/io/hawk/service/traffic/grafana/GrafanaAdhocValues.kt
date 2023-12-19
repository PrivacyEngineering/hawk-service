package io.hawk.service.traffic.grafana

class AdhocValueRequest {
    var key: String? = null
}

data class AdhocValueResult(val text: String)