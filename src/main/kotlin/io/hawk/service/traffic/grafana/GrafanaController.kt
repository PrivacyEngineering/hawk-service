package io.hawk.service.traffic.grafana

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
        val target = input["target"].takeUnless { it == "" } ?: return handlers.map(MetricHandler::target)

        return handlers
            .filter { it.target == target }
            .flatMap { it.search() }
    }

    @PostMapping("/query")
    fun query(@RequestBody request: QueryRequest): List<QueryResult> {
        if(request.from() == null || request.to() == null) return emptyList()
        return handlers
            .map { it to request.targets.firstOrNull { target -> it.target == target.target } }
            .filterNot { it.second == null }
            .flatMap { it.first.query(request, it.second!!) }
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