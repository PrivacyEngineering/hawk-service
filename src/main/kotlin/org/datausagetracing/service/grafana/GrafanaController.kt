package org.datausagetracing.service.grafana

import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class GrafanaController(
    val handlers: List<MetricHandler>
) {
    @GetMapping
    fun index() = Unit

    @PostMapping("/search")
    fun search(@RequestBody input: Map<String, String>): List<String> {
        return handlers.map(MetricHandler::target)
    }

    @PostMapping("/query")
    fun query(@RequestBody request: QueryRequest): List<QueryResult> {
        if(request.from() == null || request.to() == null) return emptyList()
        return handlers
            .filter { handler -> request.targets.any { it.target == handler.target } }
            .flatMap { it.query(request) }
    }

    @PostMapping("/tag-keys")
    fun adhocKeys(): List<AdhocKeyResult> {
        return handlers
            .map(MetricHandler::adhocKeys)
            .flatMap(Map<String, String>::entries)
            .map { AdhocKeyResult(it.value, it.key) }
    }

    @PostMapping("/tag-values")
    fun adhocValues(@RequestBody request: AdhocValueRequest): List<AdhocValueResult> {
        if(request.key == null) return emptyList()
        return handlers
            .filter { request.key in it.adhocKeys }
            .flatMap { it.adhocValues(request.key!!) }
            .map(::AdhocValueResult)
    }
}